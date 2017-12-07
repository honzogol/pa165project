package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.MonsterAgility;

import javax.validation.constraints.NotNull;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class MonsterUpdateDTO {

	@NotNull
	private Long id;

	private String name;
	private Double height;
	private Double weight;
	private MonsterAgility agility;

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

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public MonsterAgility getAgility() {
		return agility;
	}

	public void setAgility(MonsterAgility agility) {
		this.agility = agility;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MonsterUpdateDTO that = (MonsterUpdateDTO) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (height != null ? !height.equals(that.height) : that.height != null) return false;
		if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
		return agility == that.agility;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (height != null ? height.hashCode() : 0);
		result = 31 * result + (weight != null ? weight.hashCode() : 0);
		result = 31 * result + (agility != null ? agility.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "MonsterUpdateDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", height=" + height +
				", weight=" + weight +
				", agility=" + agility +
				'}';
	}
}
