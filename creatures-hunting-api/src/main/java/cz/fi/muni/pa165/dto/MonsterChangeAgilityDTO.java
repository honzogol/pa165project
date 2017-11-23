package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.MonsterAgility;

public class MonsterChangeAgilityDTO {

	private Long monsterId;

	private MonsterAgility monsterAgility;

	public MonsterChangeAgilityDTO(Long monsterId, MonsterAgility monsterAgility) {
		this.monsterId = monsterId;
		this.monsterAgility = monsterAgility;
	}

	public Long getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(Long monsterId) {
		this.monsterId = monsterId;
	}

	public MonsterAgility getMonsterAgility() {
		return monsterAgility;
	}

	public void setMonsterAgility(MonsterAgility monsterAgility) {
		this.monsterAgility = monsterAgility;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MonsterChangeAgilityDTO that = (MonsterChangeAgilityDTO) o;

		if (monsterId != null ? !monsterId.equals(that.monsterId) : that.monsterId != null) return false;
		return monsterAgility == that.monsterAgility;
	}

	@Override
	public int hashCode() {
		int result = monsterId != null ? monsterId.hashCode() : 0;
		result = 31 * result + (monsterAgility != null ? monsterAgility.hashCode() : 0);
		return result;
	}
}
