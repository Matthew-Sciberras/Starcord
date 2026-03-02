import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStateService } from '@app/core/auth/auth-state.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class HomeComponent implements OnInit{
  constructor(
    private authStateService: AuthStateService
  ) {}

  private router = inject(Router);

  ngOnInit() {
    if(!this.authStateService.isAuthenticated()) {
      this.router.navigateByUrl("/login");
    }
  }
}
