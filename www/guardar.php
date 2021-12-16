<?php
    $result = $_POST['result'];

    // ejecuto lo que necesite hacer con la variable
    // por ejemplo: le agrego el signo $
    
    // esta es una forma de devolverle el nuevo valor a
    // javascript y lo va a agarrar el "success" del ajax
    echo json_encode($result);


    $xml1=simplexml_load_file('xml/paquets.xml');
    $i=-1;
        foreach($xml1->children() as $paquet){
            $i=$i+1;

            $id_paquet = $paquet->id_paquet;
            $entregat =$paquet->entregat;

            if($id_paquet== $result )
            {
                    
                $paquet->entregat = "true";
            }

        }


        file_put_contents("xml/paquets.xml",$xml1->saveXML());

?>