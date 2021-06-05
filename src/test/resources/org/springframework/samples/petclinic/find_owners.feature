@ui
Feature: Find Owners
    We can find owners of pets. And then if needed we can manage them

  Rule: Finding owners with last names
    Example: One owner with the specific last name
      Given an owner with last name "Escobito"
       When find owner with the last name
       Then The name of owner, "Maria Escobito" will be shown in the result
        And The address of owner, "345 Maple St." will be shown in the result

    Example: More than one owner with the specific last name
      Given an owner with last name "Davis"
       When find owner with the last name
       Then multiple owners will be shown
         | Name         | Address           |
         | Betty Davis  | 638 Cardinal Ave. |
         | Harold Davis | 563 Friendly St.  |

    Example: No owner found with the specific last name
      Given an owner with last name "Wrong Last Name"
       When find owner with the last name
       Then no owners found
