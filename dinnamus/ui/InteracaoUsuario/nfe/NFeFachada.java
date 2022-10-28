/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfe;

import br.com.log.LogDinnamus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import jpa.entidades.CepMunicipio;
import jpa.entidades.CepUf;
import jpa.entidades.NfePais;
import jpa.entidades.OffNfcePdv;
import jpa.entidades.OffNfcePdvNotas;
import jpa.infra.Persistencia;
import org.hibernate.Hibernate;

/**
 *
 * @author Fernando
 */
public class NFeFachada {

    private EntityManager persistencia;

    public EntityManager getPersistencia() {

        persistencia = new Persistencia().getEntityManager();

        return persistencia;
    }

    /**
     * @param persistencia the persistencia to set
     */
    public void setPersistencia(EntityManager persistencia) {
        this.persistencia = persistencia;
    }

    public OffNfcePdv getNfeconfigPDV() {
        OffNfcePdv offNfcePdv = null;
        try {
            EntityManager persistencia1 = getPersistencia();

            Query createQuery = persistencia1.createQuery("select p  from OffNfcePdv p where p.modelo=:modelo", OffNfcePdv.class);
            createQuery.setParameter("modelo", 65);
            List resultList = createQuery.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                offNfcePdv = (OffNfcePdv) resultList.get(0);
            }
            persistencia1.close();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return offNfcePdv;
    }

    public OffNfcePdvNotas getNfe(Long id) {
        OffNfcePdvNotas Ret = null;
        EntityManager persistencia1 = getPersistencia();
        try {


            Query createQuery = persistencia1.createQuery("from OffNfcePdvNotas p where p.id=:id", OffNfcePdvNotas.class);
            createQuery.setParameter("id", id);

            List<OffNfcePdvNotas> offNotas = createQuery.getResultList();


            if (offNotas != null && !offNotas.isEmpty()) {
                Ret = offNotas.get(0);

                Hibernate.initialize(Ret.getOffNfeDestCollection());
                
                Hibernate.initialize(Ret.getOffNfeProdCollection());
            }
            persistencia1.close();

        } catch (Exception e) {
            LogDinnamus.Log(e, true);

        }
        return Ret;
    }

    public List<OffNfcePdvNotas> listarNFE() {
        List<OffNfcePdvNotas> Ret = null;
        try {
            EntityManager persistencia1 = getPersistencia();
            Query createQuery = persistencia1.createQuery("select p from OffNfcePdvNotas p where p.modelo=:modelo");
            createQuery.setParameter("modelo", 65);

            Ret = createQuery.getResultList();

            persistencia1.close();

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean gravarNFe(OffNfcePdvNotas nota) {
        boolean Ret = false;
        EntityManager persistencia1 = getPersistencia();
        if (persistencia1 != null) {
            EntityTransaction transaction = persistencia1.getTransaction();

            try {
                transaction.begin();

                persistencia1.merge(nota);

                transaction.commit();

                Ret = true;
            } catch (Exception e) {
                transaction.rollback();
                LogDinnamus.Log(e, true);
            }
            persistencia1.close();
        }
        return Ret;
    }

    public List<CepUf> getCepUF(Integer pais) {
        List<CepUf> Ret = null;
        EntityManager persistencia1 = getPersistencia();
        try {

            if (persistencia1 != null) {
                Query createQuery = persistencia1.createQuery("from CepUf c join fetch c.pais p where p.codigo=:pais order by c.nome");
                
                createQuery.setParameter("pais", pais);
                Ret = createQuery.getResultList();

            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        } finally {
            if (persistencia1 != null) {
                persistencia1.close();
            }
        }
        return Ret;
    }

    public List<CepMunicipio> getCepMunicipio(String Uf) {
        List<CepMunicipio> Ret = null;
        EntityManager persistencia1 = getPersistencia();
        try {

            if (persistencia1 != null) {
                Query createQuery =
                        persistencia1.createQuery("from CepMunicipio cm join fetch cm.uf c where c.codigo = :uf order by cm.municipio", CepMunicipio.class);

                createQuery.setParameter("uf", Uf);


                Ret = createQuery.getResultList();

            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        } finally {
            if (persistencia1 != null) {
                persistencia1.close();
            }
        }
        return Ret;
    }
    
    public List<NfePais> getNfePais() {
        List<NfePais> Ret = null;
        EntityManager persistencia1 = getPersistencia();
        try {

            if (persistencia1 != null) {
                Query createQuery =
                        persistencia1.createQuery("from NfePais p order by p.codigo", NfePais.class);

                Ret = createQuery.getResultList();

            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        } finally {
            if (persistencia1 != null) {
                persistencia1.close();
            }
        }
        return Ret;
    }
}
