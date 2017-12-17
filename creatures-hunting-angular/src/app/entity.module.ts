
export interface Monster {
  id: number;
  name: string;
  height: number;
  weight: number;
  agility: string;
}

export interface Weapon {
  id: number;
  name: string;
  appropriateMonsters: Monster[];
  type: string;
  range: number;
  magazineCapacity: number;

}

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
}

export interface Area {
  id: number;
  name: string;
  type: string;
  monsters: Monster[];
}
