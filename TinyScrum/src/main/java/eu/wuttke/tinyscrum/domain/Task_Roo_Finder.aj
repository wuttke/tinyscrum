// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Task;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Task_Roo_Finder {
    
    public static TypedQuery<Task> Task.findTasksByDeveloperOrTester(String developer, String tester) {
        if (developer == null || developer.length() == 0) throw new IllegalArgumentException("The developer argument is required");
        if (tester == null || tester.length() == 0) throw new IllegalArgumentException("The tester argument is required");
        EntityManager em = Task.entityManager();
        TypedQuery<Task> q = em.createQuery("SELECT o FROM Task AS o WHERE o.developer = :developer OR o.tester = :tester", Task.class);
        q.setParameter("developer", developer);
        q.setParameter("tester", tester);
        return q;
    }
    
}