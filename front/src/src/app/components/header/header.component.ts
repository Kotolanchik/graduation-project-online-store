import { Component, OnInit } from "@angular/core";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public screenWidth: any;
  public getScreenHeight: any;
  public showCategories: boolean = false;
  public categories: string[] = [
    "Овощи",
    "Фрукты",
    "Орехи"
  ];

  constructor() { }

  ngOnInit(): void {
    this.screenWidth = window.innerWidth;
    this.getScreenHeight = window.innerHeight;
  }

  selectCategory(category: string) {
    console.log("Выбрана категория:", category);
  }
}
