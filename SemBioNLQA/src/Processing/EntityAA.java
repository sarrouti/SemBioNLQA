/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

import java.util.List;

/**
 *
 * @author sarrouti
 */
public class EntityAA {
    
    List<AA> AAs;
    List<Entity> EEs;

    public EntityAA() {
    }

    public void setAAs(List<AA> AAs) {
        this.AAs = AAs;
    }

    public void setEEs(List<Entity> EEs) {
        this.EEs = EEs;
    }

    public List<AA> getAAs() {
        return AAs;
    }

    public List<Entity> getEEs() {
        return EEs;
    }
    
}
