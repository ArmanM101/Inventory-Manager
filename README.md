# My Personal Project

## Store Inventory Management Application

- **What will the application do?**</br>
The application I want to create is an inventory management 
system for stores. It will allow the user to create a store 
and enter what products are bought and what products are
sold so that the user will have an inventory of what is 
currently in the store. Possible features include different 
categories of items; including a bought and sold price; having 
multiple stores each with their own inventory. (X could be 
an item being sold in the store. Y could be a list of items
currently in stock).
- **Who will use it?** </br>
I intend for this application to be used by store-owners in
order to keep track of their inventory. 
- **Why is this application of interest to you?** </br>
This application is of interest to me because I feel like 
it would be useful tool if I were to ever open a store of my 
own. I'm also curious as to how advanced I can make the application.

## *User Stories*
- As a user, I want to be able to add a shipment of products to my stores inventory.
- As a user, I want to be able to open my store with a given balance.
- As a user, I want to be able to get receipts for each sale and purchase.
- As a user, I want to be able to add a sale to my store and have it reflected in my Store's balance and inventory.
- As a user, I want to be able to tell if my store had a negative or positive balance once I close it.
- As a user, I want to be able to view my store's inventory to see what I have.
- As a user, I want to be able to tell if I can make a sale with my given inventory.
- As a user, I want to be able to save my store and all its details (if I so choose to).
- As a user, I want to be able to be able to load my store and all its details (if I so choose to).

## *Phase 4: Task 2*
For this sample I performed the following actions 
- I created the store Costco with a starting balance of $1000
- I bought 20 units of Milk for $2.50 each
- I bought 12 units of Eggs for $1.25 each
- I sold 8 units of Milk for $4.50 each
- I sold 10 units of Eggs for $3.10 each
- I saved the store
- I sold 4 units of Milk for $4.10 each
- I closed the store


  Wed Aug 09 22:22:05 PDT 2023

  Created the store Costco with a starting balance of $ 1000.0

  Wed Aug 09 22:22:17 PDT 2023

  Purchased 20 units of Milk for $50.0

  Wed Aug 09 22:22:29 PDT 2023

  Purchased 12 units of Eggs for $15.0

  Wed Aug 09 22:22:39 PDT 2023

  Sold 8 units of Milk for $36.0

  Wed Aug 09 22:22:50 PDT 2023

  Sold 10 units of Eggs for $31.0

  Wed Aug 09 22:22:52 PDT 2023

  Saved the store Costco

  Wed Aug 09 22:23:03 PDT 2023

  Sold 4 units of Milk for $16.4


## *Phase 4: Task 3*
I would make a few changes to my code in order to improve its design. The first change I would make is 
in regard to the save and load functions in my console class and GUI class. At the moment I have separate 
functions for saving/loading in my GUI and console class, but most of the code is the same. I could 
make an abstract class that has a save/load function that the console and GUI classes call with differing boolean values 
so that the duplicate code is removed but the functions are still able to preform both tasks by using the boolean value 
for the class-specific code.

Another change I would make to my code is making the list of products assigned to the store, not editable by 
classes other than Store. The reason I would do this is everytime a user tries to make a sale 
I have the code attempt find the product within the inventory and see if there 
is enough of that product to make the sale. However, I ran into the issue of even if the sale couldn't be made, 
the code would still edit the product in the inventory. To fix this I designed the code, so it would
find the product in the inventory and then made an identical replica that didn't belong to the 
inventory so any edits to it wouldn't matter. However, this required a lot of extra code 
that could be scrapped by simply making it so only the Store class could edit the inventory. This would
allow my code to be more coherent and concise.
