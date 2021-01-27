
function fnGetTxnCharges(paytype,amount,pubkey,pvtkey,url){
var qsgetcharges="pmt";
var rulesgetcharges="jgettransactioncharges";
var txnCharges ='';
// fire ajax to get transaction charges
var jvariables = JSON.stringify({ qs: qsgetcharges, rules:rulesgetcharges, apikey:pubkey, pvtkey:pvtkey, paytype:paytype, amount:amount });

          $.ajax({
                      async:false,
                      beforeSend: function(xhr){  xhr.overrideMimeType( "text/plain; charset=x-user-defined" );},// Include this line to specify what Mime type the xhr response is going to be
                      url: url,  type: "POST", dataType: "json",  data:{ objarray : jvariables },
                      success: function (result) {
                        console.log("Result "+result);
                        if (result) {
                          if(result['error']=='false'){
                            console.log("Result is"+result);
                            txnCharges=result.transactioncharges;
                            console.log("Transaction charge in Fn  is"+txnCharges);
                            
                          }else if(result['error']=='true'){
                        
                          txnCharges = 0.00;

                          }


                        }
                      }
                    });
        
     return txnCharges;
}