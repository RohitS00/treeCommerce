import { Component, OnInit } from '@angular/core';
import { PurchaseOrder } from 'src/app/utils/modals/PurchaseOrder';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-consumer-order',
  templateUrl: './consumer-order.component.html',
  styleUrls: ['./consumer-order.component.css']
})
export class ConsumerOrderComponent implements OnInit{
  orders: PurchaseOrder[] = [];

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {}

  ngOnInit() {
    const userId = this.authenticationService.getUserId();
    console.log(userId)
    this.userService.getOrdersById(userId).subscribe(
      orders => {
        this.orders = orders;
        console.log(orders)
      },
      error => {
        console.error(error);
      }
    );
  }


}
