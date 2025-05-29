package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


	
	
	@Entity
	@Table(name = "tw_historia_ingreso_producto")
	@NamedQuery(name = "TwHistoriaIngresoProducto.findAll", query = "SELECT t FROM TwHistoriaIngresoProducto t")
	public class TwHistoriaIngresoProducto implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "n_id")
		private Long nId;	
		
		@Column(name = "n_id_pedido")
		private Long nIdPedido;
		@Column(name = "n_id_bodega")
		private Long nIdBodega;
		@Column(name = "n_id_producto")
		private Long nIdProducto;
		@Column(name = "n_cantidad")
		private int nCantidad;
		@Column(name = "n_id_usuario")
		private Long nIdUsuario;
		@Column(name = "d_fecha_ingreso")
		private LocalDateTime dFechaingreso;
		
		

		@ManyToOne
		@JoinColumn(name = "n_id_producto", insertable = false, updatable = false)
		private TcProducto tcProducto;
		
		@ManyToOne
		@JoinColumn(name = "n_id_usuario",  insertable = false, updatable = false)
		private TcUsuario tcUsuario;
		
		@ManyToOne
		@JoinColumn(name = "n_id_pedido",  insertable = false, updatable = false)
		private TwPedido twPedido;
		
		@ManyToOne
		@JoinColumn(name = "n_id_bodega",  insertable = false, updatable = false)
		private TcBodega TcBodega;
		
		public TwHistoriaIngresoProducto() {
			
		}

		public Long getnId() {
			return nId;
		}

		public void setnId(Long nId) {
			this.nId = nId;
		}

		public Long getnIdPedido() {
			return nIdPedido;
		}

		public void setnIdPedido(Long nIdPedido) {
			this.nIdPedido = nIdPedido;
		}

		public Long getnIdBodega() {
			return nIdBodega;
		}

		public void setnIdBodega(Long nIdBodega) {
			this.nIdBodega = nIdBodega;
		}

		public Long getnIdProducto() {
			return nIdProducto;
		}

		public void setnIdProducto(Long nIdProducto) {
			this.nIdProducto = nIdProducto;
		}

		

		public int getnCantidad() {
			return nCantidad;
		}

		public void setnCantidad(int nCantidad) {
			this.nCantidad = nCantidad;
		}

		public Long getnIdUsuario() {
			return nIdUsuario;
		}

		public void setnIdUsuario(Long nIdUsuario) {
			this.nIdUsuario = nIdUsuario;
		}

		

		

		public LocalDateTime getdFechaingreso() {
			return dFechaingreso;
		}

		public void setdFechaingreso(LocalDateTime dFechaingreso) {
			this.dFechaingreso = dFechaingreso;
		}

		public TcProducto getTcProducto() {
			return tcProducto;
		}

		public void setTcProducto(TcProducto tcProducto) {
			this.tcProducto = tcProducto;
		}

		public TcUsuario getTcUsuario() {
			return tcUsuario;
		}

		public void setTcUsuario(TcUsuario tcUsuario) {
			this.tcUsuario = tcUsuario;
		}

		public TwPedido getTwPedido() {
			return twPedido;
		}

		public void setTwPedido(TwPedido twPedido) {
			this.twPedido = twPedido;
		}

		public TcBodega getTcBodega() {
			return TcBodega;
		}

		public void setTcBodega(TcBodega tcBodega) {
			TcBodega = tcBodega;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
		public String toString() {
			return "TwHistoriaIngresoProducto [nId=" + nId + ", nIdPedido=" + nIdPedido + ", nIdBodega=" + nIdBodega
					+ ", nIdProducto=" + nIdProducto + ", nCantidad=" + nCantidad + ", nIdUsuario=" + nIdUsuario
					+ ", dFechaingreso=" + dFechaingreso + ", tcProducto=" + tcProducto + ", tcUsuario=" + tcUsuario
					+ ", twPedido=" + twPedido + ", TcBodega=" + TcBodega + "]";
		}
		
		
		


}
