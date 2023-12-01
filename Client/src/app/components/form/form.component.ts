import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../services/person.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})


export class FormComponent implements OnInit {
  private inAddressLibraryInstance:any;
  
  fname!: string;
  lname!: string;
  addressValue!: string;


  constructor(private personService: PersonService) {}
  
  ngOnInit(): void {
    this.inAddressLibraryInstance = new InAadress({"container":"InAddressDiv","mode":3,"ihist":"1993","appartment":0,"lang":"et"});
    this.getAddressInput();
  }
  
  
  onSubmit() {
    if (this.fname == null || this.lname == null || this.addressValue == null) {
      alert('Lisa eesnimi, perenimi ning aadress.');
      return;
    }
    
    const newPerson = {
      firstName: this.fname,
      lastName: this.lname,
      address: this.addressValue
    }

    
    this.personService.addPersons(newPerson).subscribe({
      error: (err) => alert(err)
    });
    
    // Tühjendame ära aadressite loendi
    this.clearFormInputs();
}

  
  public getAddressInput(): void {
    document.addEventListener('addressSelected', (e: any) => {
      this.addressValue =  e.detail[0].aadress;
    });
  }

  public clearFormInputs(): void {
    this.fname = "";
    this.lname= "";
    this.inAddressLibraryInstance.setAddress("");
    this.inAddressLibraryInstance.hideResult();
  }
}
