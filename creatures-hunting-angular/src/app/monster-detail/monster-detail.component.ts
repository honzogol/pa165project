import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Monster } from '../entity.module';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-monster-detail',
  templateUrl: './monster-detail.component.html',
  styleUrls: ['./monster-detail.component.scss']
})
export class MonsterDetailComponent implements OnInit {

  monsterId: number;
  showMonster: boolean = false;
  monster: Monster;
  selectedAgility: string;

    constructor(private http: HttpClient, private route: ActivatedRoute) {
    this.route.params.subscribe(res => this.monsterId = res.id);
  }

  ngOnInit() {
    this.http.get<Monster>('http://localhost:8080/pa165/rest/monsters/' + this.monsterId).subscribe(
      data => {
        this.monster = data;
        this.showMonster = true;
        this.selectedAgility = data.agility == null ? 'null' : data.agility;
      });
  }
}
