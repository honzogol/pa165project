package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.WeaponType;

import java.util.HashSet;
import java.util.Set;

/**
 * WeaponCreateDTO class
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class WeaponCreateDTO {

    private String name;
    private WeaponType type;
    private Set<MonsterDTO> appropriateMonsters = new HashSet<>();
    private Integer range;
    private Integer magazineCapacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public Set<MonsterDTO> getAppropriateMonsters() {
        return appropriateMonsters;
    }

    public void addAppropriateMonster(MonsterDTO monsterDTO){
        appropriateMonsters.add(monsterDTO);
    }

    public void removeAppropriateMonster(MonsterDTO monsterDTO){
        appropriateMonsters.remove(monsterDTO);
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer getMagazineCapacity() {
        return magazineCapacity;
    }

    public void setMagazineCapacity(Integer magazineCapacity) {
        this.magazineCapacity = magazineCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof WeaponDTO)) return false;

        WeaponDTO weaponDTO = (WeaponDTO) o;

        if (getName() != null ? !getName().equals(weaponDTO.getName()) : weaponDTO.getName() != null) return false;
        if (getType() != weaponDTO.getType()) return false;
        if (getAppropriateMonsters() != null ? !getAppropriateMonsters().equals(weaponDTO.getAppropriateMonsters()) : weaponDTO.getAppropriateMonsters()!= null)
            return false;
        if (getRange() != null ? !getRange().equals(weaponDTO.getRange()) : weaponDTO.getRange() != null) return false;
        return getMagazineCapacity() != null ? getMagazineCapacity().equals(weaponDTO.getMagazineCapacity()) : weaponDTO.getMagazineCapacity()== null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getAppropriateMonsters() != null ? getAppropriateMonsters().hashCode() : 0);
        result = 31 * result + (getRange() != null ? getRange().hashCode() : 0);
        result = 31 * result + (getMagazineCapacity()!= null ? getMagazineCapacity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeaponDTO{" +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", appropriateMonsters=" + getAppropriateMonsters()+
                ", range=" + getRange() +
                ", magazineCapacity=" + getMagazineCapacity()+
                '}';
    }
}
