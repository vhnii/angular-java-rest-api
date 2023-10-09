import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../services/person.service';

import {Person} from '../../Types/Person';


@Component({
  selector: 'app-persons',
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css']
})
export class PersonsComponent implements OnInit {

  
  persons: Person[] = [];
  
  constructor(private personService: PersonService) {}
  
  ngOnInit(): void {
    this.personService.getPersons().subscribe((data: Person[]) => console.log(this.persons = [...data]));
  }
}
