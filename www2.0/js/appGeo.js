var app = {
  init:function(){
      navigator.geolocation.getCurrentPosition(app.onSucces,app.onError);
      var info_coord = document.getElementById("info_coord");
  },
 
  onSucces:function(position){

         map = L.map('zona_mapa').setView([position.coords.latitude, position.coords.longitude], 17);

         L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
             attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
         }).addTo(map);

          L.marker([position.coords.latitude, position.coords.longitude],{

            icon: L.icon({      
            iconUrl: "img/camion.png",
            iconSize: [70, 50]
        })
      }).bindPopup(
            "<h3>Estàs aqui</h3>"
            ).addTo(map)
          inicio();        
  },
  onError:function(error){
      info_coord.innerHTML = "<p>Error: "+ error.code + ". Missatge: "+ error.message + "</p>"
  }
}
  
function inicio() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
          lecturaXML(this);
      }
  };
  xhttp.open("GET", "http://192.168.1.193/paquets.xml", true);
  // xhttp.open("GET", "http://172.20.10.12/paquets.xml", true);

  //xhttp.open("GET", "xml/paquets.xml", true);
  xhttp.send();
  }
  
  function lecturaXML(xml) {
  var xmlDoc = xml.responseXML;
  
      for (var i = 0; i < xmlDoc.getElementsByTagName("paquet").length; i++) {
        
          var latitud = xmlDoc.getElementsByTagName("latitud")[i].childNodes[0].nodeValue; 
          var longitud = xmlDoc.getElementsByTagName("longitud")[i].childNodes[0].nodeValue;
          idpaquet = xmlDoc.getElementsByTagName("id_paquet")[i].childNodes[0].nodeValue;
          numpaquet = xmlDoc.getElementsByTagName("num_envio")[i].childNodes[0].nodeValue;
          estat = xmlDoc.getElementsByTagName("entregat")[i].childNodes[0].nodeValue;
   
          console.log(idpaquet+estat);
          var popup2 = 
          '<div id = "popup">'+
          '<h2>Entrega</h2>'+
          '<p>'+'ID PAQUET: '+idpaquet+'</p>'+
          '<p>'+'Nº ENVIO: '+numpaquet+'</p>'+
          '<p id="entregatP"></p>'+
          '<div id = "popupimg">'+
          '<img id="Foto">'+
          '</div>'+
         '<div id = "botons">'+
          '<button id = "boto" onclick="cameraFoto()">CAMARA</button>'+
          '<button id = "boto" onclick="entregarP()">ENTREGAR</button>'+
          '<button id = "boto" onclick="recargar()">RECARGAR</button>'+
          '</div>'+
          '</div>';

  

        if (estat == "true"){
            marker = new L.marker([latitud,longitud],{

                icon: L.icon({      
                iconUrl: "img/marker_entregat.png",
                iconSize: [30, 50]
            })
  
            }).bindPopup(popup2).addTo(map);
            marker.on('click', onClick);
        } else {
            marker = new L.marker([latitud,longitud],{

                icon: L.icon({      
                iconUrl: "img/marker_noentregat.png",
                iconSize: [30, 50]
            })
  
            }).bindPopup(popup2).addTo(map);
            marker.on('click', onClick);
        }
      }
  }

function cameraFoto() { 
  navigator.camera.getPicture(onSuccess, onFail, {  
     quality: 100, 
     destinationType: Camera.DestinationType.DATA_URL ,
     correctOrientation: true
  });  
  
  function onSuccess(imageData) { 
      image = document.getElementById('Foto'); 
     image.src = "data:image/jpeg;base64," + imageData; 
  }  
  
  function onFail(message) { 
     alert('Failed because: ' + message); 
  } 
}

function entregarP(){
$.ajax({
  method: 'post',
  url: 'http://192.168.1.193/guardar.php',

  data: {result},

  success: function(response) {
    console.log(response);
  }
});

now = new Date().toLocaleString()
console.log(now);

 doc = new jsPDF(); 
   doc.text('Paquet entregat!', 10, 10);
   doc.text('ID PAQUET:',10,25);
   doc.text(result,55,25);
   doc.text('NUM PAQUET:',10,35);
   doc.text(numpaquetpdf,55,35);
   doc.text('A data i hora:',10,45);
   doc.text(now,55,45);
   doc.save('oraidix.pdf');
}

function onClick(e) {
  var popup = e.target.getPopup();
  var content = popup.getContent();

  console.log(content);
 result = content.substring(49, 48);
console.log(result);
numpaquetpdf = content.substring(66,72);
console.log(numpaquetpdf);
}

function recargar(){
  location.reload();
}

document.addEventListener('deviceready',app.init());