# EasyShop Update V2
## December 20th, 2024
![EasyShop](https://github.com/stephano47/EasyShop/blob/main/src/photos/Screenshot%202024-12-19%20134111.png?raw=true)

Hey there,

We're excited to share the latest update for EasyShop! In this version, we've focused on fixing some critical bugs and improving the user experience. Below, you'll find details on what's been fixed and the issues we're still working on.

## Fixes

### Search Function:
You may have noticed that the product search was returning incorrect results. We've fixed this issue, so you should now get the correct products when searching.

### Duplication of Items:
Some products were appearing duplicated with only minimal differences. This was due to the method using create instead of update. 

## Bugs Found

1. **Category Load Failure**:
    - I noticed while testing the EasyShop site that occasionally, the category loading might fail.
    - If this happens, refreshing the page fixes the issue.
    - This happens whenever I went into the cart section for a couple of seconds then head back into the home screen.\
![LoadFail](https://github.com/stephano47/EasyShop/blob/main/src/photos/Screenshot%202024-12-19%20092948.png?raw=true)

 