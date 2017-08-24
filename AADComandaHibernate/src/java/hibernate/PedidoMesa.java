package hibernate;

public class PedidoMesa {
    
    private Pedido pedido;
    private Mesa mesa;

    public PedidoMesa() {
        
    }

    public PedidoMesa(Pedido pedido, Mesa mesa) {
        this.pedido = pedido;
        this.mesa = mesa;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    @Override
    public String toString() {
        return "JoinPedidoMesa{" + "pedido=" + pedido + ", mesa=" + mesa + '}';
    }
    
}
