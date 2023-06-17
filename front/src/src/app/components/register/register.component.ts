import {Component, OnInit} from '@angular/core';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css', '../../../styles.css']
})
export class RegisterComponent implements OnInit {
  ngOnInit(): void {

  }
  login() {

  }
  busy = false;
  username = '';
  password = '';
  loginError = false;
  isInvalidUsername: boolean = true
  isInvalidEmail: boolean = true
  isInvalidPassword: boolean = true

}
