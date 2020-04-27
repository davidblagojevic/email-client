function getAccount() {
    var url = window.location; // http://google.com?id=test
    var urlObject = new URL(url);
    var account = urlObject.searchParams.get('account');
    console.log(account);
    if (account === null){
        window.location.replace('email-accounts.html')
        return; 
    }
    return account;
    
}