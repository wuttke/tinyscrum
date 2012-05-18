// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Iteration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Iteration_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Iteration.entityManager;
    
    public static final EntityManager Iteration.entityManager() {
        EntityManager em = new Iteration().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Iteration.countIterations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Iteration o", Long.class).getSingleResult();
    }
    
    public static List<Iteration> Iteration.findAllIterations() {
        return entityManager().createQuery("SELECT o FROM Iteration o", Iteration.class).getResultList();
    }
    
    public static Iteration Iteration.findIteration(Long id) {
        if (id == null) return null;
        return entityManager().find(Iteration.class, id);
    }
    
    public static List<Iteration> Iteration.findIterationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Iteration o", Iteration.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Iteration.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Iteration.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Iteration attached = Iteration.findIteration(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Iteration.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Iteration.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Iteration Iteration.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Iteration merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
