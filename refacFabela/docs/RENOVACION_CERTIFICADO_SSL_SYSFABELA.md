# Renovacion de Certificado SSL en sysfabela.com

## Objetivo

Este documento describe el procedimiento para reemplazar el certificado SSL de `sysfabela.com` y `www.sysfabela.com` en el servidor Ubuntu con Apache.

Aplica al servidor actual donde:

- Apache sirve el frontend en `https://sysfabela.com`
- `https://www.sysfabela.com` redirige a `https://sysfabela.com`
- La API se publica por proxy hacia `http://127.0.0.1:4043/api`

## Estado actual esperado

Archivos Apache relevantes:

- `/etc/apache2/sites-available/000-default.conf`
- `/etc/apache2/sites-available/default-ssl.conf`

Rutas actuales de certificados:

- Certificado servidor: `/etc/ssl/certs/scs1764211508530_sysfabela.com_server.crt`
- Llave privada: `/etc/ssl/private/scs1764211508530_sysfabela.com_server.key`
- Certificado CA/intermedio: `/etc/ssl/certs/scs1764211508530_sysfabela.com_ca.crt`
- Fullchain recomendado: `/etc/ssl/certs/sysfabela_fullchain.crt`

## Antes de iniciar

Confirmar que el nuevo certificado cubra ambos nombres:

- `sysfabela.com`
- `www.sysfabela.com`

Validar que se tengan estos archivos del proveedor:

- Certificado del sitio (`server.crt`)
- Certificado intermedio o CA bundle (`ca.crt`, `bundle.crt` o similar)
- Llave privada (`server.key`)

Si el proveedor entrega el certificado en otro formato, convertirlo antes de usarlo en Apache.

## Respaldo obligatorio

Entrar al servidor y generar respaldo antes de tocar Apache:

```bash
mkdir -p /root/respaldos_ssl_sysfabela
cp /etc/apache2/sites-available/000-default.conf /root/respaldos_ssl_sysfabela/000-default.conf.bak
cp /etc/apache2/sites-available/default-ssl.conf /root/respaldos_ssl_sysfabela/default-ssl.conf.bak
cp /etc/ssl/certs/scs1764211508530_sysfabela.com_server.crt /root/respaldos_ssl_sysfabela/ 2>/dev/null
cp /etc/ssl/certs/scs1764211508530_sysfabela.com_ca.crt /root/respaldos_ssl_sysfabela/ 2>/dev/null
cp /etc/ssl/certs/sysfabela_fullchain.crt /root/respaldos_ssl_sysfabela/ 2>/dev/null
cp /etc/ssl/private/scs1764211508530_sysfabela.com_server.key /root/respaldos_ssl_sysfabela/ 2>/dev/null
```

## Paso 1. Copiar el nuevo certificado al servidor

Copiar los nuevos archivos a rutas seguras. Ejemplo:

```bash
cp nuevo_sysfabela_server.crt /etc/ssl/certs/
cp nuevo_sysfabela_ca.crt /etc/ssl/certs/
cp nuevo_sysfabela_server.key /etc/ssl/private/
chmod 600 /etc/ssl/private/nuevo_sysfabela_server.key
chown root:root /etc/ssl/private/nuevo_sysfabela_server.key
```

Si vas a reutilizar el mismo nombre de archivo, primero confirma que el respaldo ya existe.

## Paso 2. Construir el fullchain

Apache debe servir la cadena completa. La forma recomendada es concatenar el certificado del sitio con el certificado intermedio.

Ejemplo:

```bash
cat /etc/ssl/certs/nuevo_sysfabela_server.crt /etc/ssl/certs/nuevo_sysfabela_ca.crt > /etc/ssl/certs/sysfabela_fullchain.crt
chmod 644 /etc/ssl/certs/sysfabela_fullchain.crt
chown root:root /etc/ssl/certs/sysfabela_fullchain.crt
```

Nota:

- Si el archivo `nuevo_sysfabela_ca.crt` ya incluye intermedio y raiz, normalmente funcionara, pero lo ideal es incluir solo el intermedio.
- No usar rutas locales de Windows ni rutas temporales fuera de `/etc/ssl`.

## Paso 3. Actualizar Apache

Editar `/etc/apache2/sites-available/default-ssl.conf` para que use el nuevo `fullchain` y la llave privada correcta.

Configuracion esperada:

```apache
<IfModule mod_ssl.c>
    <VirtualHost *:443>
        ServerName sysfabela.com
        ServerAdmin webmaster@localhost
        DocumentRoot /var/www/html/refacFabela

        SSLEngine on
        SSLCertificateFile /etc/ssl/certs/sysfabela_fullchain.crt
        SSLCertificateKeyFile /etc/ssl/private/nuevo_sysfabela_server.key

        ProxyPreserveHost On
        ProxyPass /api http://127.0.0.1:4043/api
        ProxyPassReverse /api http://127.0.0.1:4043/api

        TimeOut 20000

        ErrorLog ${APACHE_LOG_DIR}/error_ssl.log
        CustomLog ${APACHE_LOG_DIR}/access_ssl.log combined

        <FilesMatch "\.(cgi|shtml|phtml|php)$">
            SSLOptions +StdEnvVars
        </FilesMatch>

        <Directory /usr/lib/cgi-bin>
            SSLOptions +StdEnvVars
        </Directory>

        <Directory /var/www/html/refacFabela>
            AllowOverride All
            Require all granted
        </Directory>
    </VirtualHost>

    <VirtualHost *:443>
        ServerName www.sysfabela.com
        ServerAdmin webmaster@localhost
        DocumentRoot /var/www/html/refacFabela

        SSLEngine on
        SSLCertificateFile /etc/ssl/certs/sysfabela_fullchain.crt
        SSLCertificateKeyFile /etc/ssl/private/nuevo_sysfabela_server.key

        RedirectMatch 301 ^/(.*)$ https://sysfabela.com/$1

        ErrorLog ${APACHE_LOG_DIR}/error_ssl_www.log
        CustomLog ${APACHE_LOG_DIR}/access_ssl_www.log combined
    </VirtualHost>
</IfModule>
```

Validar tambien que `/etc/apache2/sites-available/000-default.conf` mantenga las redirecciones a HTTPS:

```apache
<VirtualHost *:80>
    ServerName sysfabela.com
    ServerAdmin webmaster@localhost
    DocumentRoot /var/www/html/refacFabela

    RedirectMatch 301 ^/(.*)$ https://sysfabela.com/$1

    ErrorLog ${APACHE_LOG_DIR}/error.log
    CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>

<VirtualHost *:80>
    ServerName www.sysfabela.com
    ServerAdmin webmaster@localhost
    DocumentRoot /var/www/html/refacFabela

    RedirectMatch 301 ^/(.*)$ https://sysfabela.com/$1

    ErrorLog ${APACHE_LOG_DIR}/error_www.log
    CustomLog ${APACHE_LOG_DIR}/access_www.log combined
</VirtualHost>
```

## Paso 4. Validar configuracion antes de recargar

```bash
apachectl configtest
```

Salida esperada:

```text
Syntax OK
```

Si falla, no recargar Apache hasta corregir el error.

## Paso 5. Recargar Apache

```bash
systemctl reload apache2
```

Si por alguna razon `reload` no aplica correctamente, usar:

```bash
systemctl restart apache2
```

## Paso 6. Validaciones posteriores

### Validacion 1. Respuesta HTTP/HTTPS

```bash
curl -I http://sysfabela.com
curl -I http://www.sysfabela.com
curl -I https://sysfabela.com
curl -I https://www.sysfabela.com
```

Resultado esperado:

- `http://sysfabela.com` redirige a `https://sysfabela.com`
- `http://www.sysfabela.com` redirige a `https://sysfabela.com`
- `https://sysfabela.com` responde `200`
- `https://www.sysfabela.com` responde `301` a `https://sysfabela.com`

### Validacion 2. Certificado y cadena

```bash
openssl s_client -connect sysfabela.com:443 -servername sysfabela.com -showcerts </dev/null
```

Resultado esperado al final:

```text
Verification: OK
Verify return code: 0 (ok)
```

### Validacion 3. Fechas del certificado

```bash
openssl x509 -in /etc/ssl/certs/sysfabela_fullchain.crt -noout -subject -issuer -dates
```

Revisar:

- `subject`
- `issuer`
- `notBefore`
- `notAfter`

### Validacion 4. Nombres cubiertos por el certificado

```bash
openssl x509 -in /etc/ssl/certs/sysfabela_fullchain.crt -noout -text | grep -A2 "Subject Alternative Name"
```

Debe incluir:

- `DNS:sysfabela.com`
- `DNS:www.sysfabela.com`

## Paso 7. Verificar el servicio desde un navegador

Probar en un equipo cliente:

1. Abrir `https://sysfabela.com`
2. Confirmar que no aparece advertencia de certificado
3. Abrir `https://www.sysfabela.com`
4. Confirmar que redirige a `https://sysfabela.com`

Si algun usuario sigue viendo el certificado anterior, pedirle:

1. Cerrar navegador
2. Volver a abrir
3. Probar en modo incognito
4. Si persiste, ejecutar `ipconfig /flushdns` en Windows

## Rollback rapido

Si el nuevo certificado falla, restaurar respaldos:

```bash
cp /root/respaldos_ssl_sysfabela/000-default.conf.bak /etc/apache2/sites-available/000-default.conf
cp /root/respaldos_ssl_sysfabela/default-ssl.conf.bak /etc/apache2/sites-available/default-ssl.conf
cp /root/respaldos_ssl_sysfabela/scs1764211508530_sysfabela.com_server.crt /etc/ssl/certs/ 2>/dev/null
cp /root/respaldos_ssl_sysfabela/scs1764211508530_sysfabela.com_ca.crt /etc/ssl/certs/ 2>/dev/null
cp /root/respaldos_ssl_sysfabela/sysfabela_fullchain.crt /etc/ssl/certs/ 2>/dev/null
cp /root/respaldos_ssl_sysfabela/scs1764211508530_sysfabela.com_server.key /etc/ssl/private/ 2>/dev/null
apachectl configtest
systemctl reload apache2
```

## Checklist operativa

- [ ] Respaldar configuracion Apache y certificados actuales
- [ ] Copiar nuevo `server.crt`, `ca.crt` y `server.key`
- [ ] Generar `/etc/ssl/certs/sysfabela_fullchain.crt`
- [ ] Actualizar `default-ssl.conf` con el nuevo `fullchain` y `key`
- [ ] Confirmar que `000-default.conf` siga redirigiendo a HTTPS
- [ ] Ejecutar `apachectl configtest`
- [ ] Ejecutar `systemctl reload apache2`
- [ ] Ejecutar `curl -I` para `sysfabela.com` y `www.sysfabela.com`
- [ ] Ejecutar `openssl s_client` y confirmar `Verify return code: 0 (ok)`
- [ ] Validar desde navegador

## Comandos resumidos

```bash
mkdir -p /root/respaldos_ssl_sysfabela
cp /etc/apache2/sites-available/000-default.conf /root/respaldos_ssl_sysfabela/000-default.conf.bak
cp /etc/apache2/sites-available/default-ssl.conf /root/respaldos_ssl_sysfabela/default-ssl.conf.bak

cp nuevo_sysfabela_server.crt /etc/ssl/certs/
cp nuevo_sysfabela_ca.crt /etc/ssl/certs/
cp nuevo_sysfabela_server.key /etc/ssl/private/
chmod 600 /etc/ssl/private/nuevo_sysfabela_server.key

cat /etc/ssl/certs/nuevo_sysfabela_server.crt /etc/ssl/certs/nuevo_sysfabela_ca.crt > /etc/ssl/certs/sysfabela_fullchain.crt

apachectl configtest
systemctl reload apache2

curl -I https://sysfabela.com
curl -I https://www.sysfabela.com
openssl s_client -connect sysfabela.com:443 -servername sysfabela.com -showcerts </dev/null
```