
var i;
var ps = document.getElementsByClassName('minlength-p');

/** 
 * 
 *  code_html: codigo html para agregar al codigo <a href="#">Read more</a>
 */
function limitandoPalabras(clase_o_ID, tamanho_max, code_html){

    for(i=0;i<clase_o_ID.length;i++) {
        // console.log(ps[i].className);
      if(clase_o_ID[i].className == 'minlength-p') {
        if(clase_o_ID[i].innerText.length < tamanho_max){
            console.log(clase_o_ID[i].innerText.length);
            clase_o_ID[i].innerHTML = clase_o_ID[i].innerHTML.substring(0,tamanho_max);
        }else{            
            clase_o_ID[i].innerHTML = clase_o_ID[i].innerHTML.substring(0,tamanho_max) + code_html;
        }
      }
    }
}

limitandoPalabras(ps, 80, '...');
