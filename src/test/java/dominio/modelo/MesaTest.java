package dominio.modelo;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
/**
 *
 * @author inici4rsesi0n
 */
class MesaTest{
    @Test
    void constructorRechazaNumeroNulo(){
        assertThatThrownBy(()->new Mesa(null, new Zona("Salón")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Número");
    }
    @Test
    void constructorRechazaNumeroVacio(){
        assertThatThrownBy(()->new Mesa("", new Zona("Salón")))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void constructorRechazaZonaNula(){
        assertThatThrownBy(()->new Mesa("M1", (Zona)null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Zona");
    }
    @Test
    void constructorRechazaZonaVacia(){
        assertThatThrownBy(()->new Mesa("M1", new Zona("")))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void mesaNuevaEstaLibre(){
        Mesa m=new Mesa("M1", "Salón");
        assertThat(m.estaLibre()).isTrue();
        assertThat(m.getEstado()).isEqualTo(EstadoMesa.LIBRE);
    }
    @Test
    void ocuparFunciona(){
        Mesa m=new Mesa("M1", "Salón");
        m.ocupar();
        assertThat(m.estaLibre()).isFalse();
        assertThat(m.getEstado()).isEqualTo(EstadoMesa.OCUPADO);
    }
    @Test
    void ocuparDosVecesFalla(){
        Mesa m=new Mesa("M1", "Salón");
        m.ocupar();
        assertThatThrownBy(m::ocupar)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ocupada");
    }
    @Test
    void liberarFunciona(){
        Mesa m=new Mesa("M1", "Salón");
        m.ocupar();
        m.liberar();
        assertThat(m.estaLibre()).isTrue();
    }
    @Test
    void liberarDosVecesFalla(){
        Mesa m=new Mesa("M1", "Salón");
        assertThatThrownBy(m::liberar)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("libre");
    }
    @Test
    void equalsPorNumeroYZona(){
        Mesa m1=new Mesa("M1", new Zona("Salón"));
        Mesa m2=new Mesa("M1", new Zona("Salón"));
        assertThat(m1).isEqualTo(m2);
    }
    @Test
    void equalsFalsoConDistintaZona(){
        Mesa m1=new Mesa("M1", new Zona("Salón"));
        Mesa m2=new Mesa("M1", new Zona("Terraza"));
        assertThat(m1).isNotEqualTo(m2);
    }
    @Test
    void equalsFalsoConDistintoNumero(){
        Mesa m1=new Mesa("M1", new Zona("Salón"));
        Mesa m2=new Mesa("M2", new Zona("Salón"));
        assertThat(m1).isNotEqualTo(m2);
    }
    @Test
    void hashCodeIgualParaMismosDatos(){
        Mesa m1=new Mesa("M1", new Zona("Salón"));
        Mesa m2=new Mesa("M1", new Zona("Salón"));
        assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
    }
    @Test
    void settersFuncionan(){
        Mesa m=new Mesa("M1", "Salón");
        m.setNumero("M2");
        m.setZona(new Zona("Terraza"));
        assertThat(m.getNumero()).isEqualTo("M2");
        assertThat(m.getZona().getNombre()).isEqualTo("Terraza");
    }
}
