import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { Monster } from '../entity.module';

@Component({
  selector: 'app-monsters',
  templateUrl: './monsters.component.html',
  styleUrls: ['./monsters.component.scss']
})
export class MonstersComponent implements OnInit {

  displayedColumns = ['id', 'name', 'agility', 'edit', 'remove'];
  showMonsters: boolean = false;
  monsters: Monster[] = [];
  dataSource: MatTableDataSource<Monster>;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadMonsters();
  }

  loadMonsters() {
    this.http.get<Monster[]>('http://localhost:8080/pa165/rest/monsters/').subscribe(
      data => {
        this.monsters = data;
        this.dataSource = new MatTableDataSource(this.monsters);
        console.log('Monsters loaded');
        console.log(data);
        this.showMonsters = true;
      },
      error => {
        console.log(error);
      });
  }

  removeMonster(id) {
    this.http.delete('http://localhost:8080/pa165/rest/monsters/' + id,  {responseType: 'text'}).subscribe(
      data => {
        this.loadMonsters();
      }
    );
  }
}

