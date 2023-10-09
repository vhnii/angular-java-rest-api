import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PersonsComponent } from './components/persons/persons.component';
import { FormComponent } from './components/form/form.component';

const routes: Routes = [
  { path: '', component: FormComponent},
  { path: 'persons', component: PersonsComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
