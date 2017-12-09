package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.AreaType;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public class AreaUpdateDTO {

    @NotNull

    private Long id;
    private String name;
    private AreaType type;
    private Set<Long> monsterIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AreaType getType() {
        return type;
    }

    public void setType(AreaType type) {
        this.type = type;
    }

    public Set<Long> getMonsterIds() {
        return monsterIds;
    }

    public void addMonster(Long monsterId) {
        monsterIds.add(monsterId);
    }

    public void removeMonster(long monsterId) {
        monsterIds.remove(monsterId);
    }

    public Set<Long> getMonsters() {
        return monsterIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AreaUpdateDTO that = (AreaUpdateDTO) o;

        if (name != null ? !name.equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (type != that.getType()) {
            return false;
        }
        return monsterIds != null ? !monsterIds.equals(that.getMonsters()) : that.getMonsters() != null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 83 * result + (type != null ? type.hashCode() : 0);
        result = 83 * result + (monsterIds != null ? monsterIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AreaDTO{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", type=" + type
                + ", monsterIds=" + monsterIds
                + '}';
    }
}
