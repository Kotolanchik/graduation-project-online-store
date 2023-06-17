import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css', '../../../styles.css']
})
export class AuthComponent implements OnInit {
  constructor(private router: Router) {}

  isInvalidUsername: boolean = true;
  isInvalidPassword: boolean = true;
  loading = false;
  username: string = '';
  password: string = '';

  ngOnInit(): void {}

  login() {
    this.loading = true;
    const mockJwt = 'mocked-jwt-token';
    localStorage.setItem('auth_token', mockJwt);
    this.loading = false;
    this.router.navigate(['/']);
  }
}
