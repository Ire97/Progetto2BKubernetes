# Progetto2BKubernetes

Progetto aggiuntivo adattato all'utilizzo di Kubernetes per corso DSBD 2020/2021

## Autori

1) Irene Baldacchino (1000012344);
2) Salvatore Gambadoro (1000008650).

## Modifiche effettuate rispetto al progetto precedente

Sono state apportate diverse modifiche rispetto al progetto precedente che riguardano:
1) Aggiunta di variabili dinamiche al fine di evitare un continuo push dell'immagine;
2) Aggiunta di un nuovo file di configurazione di Kafka per quanto riguarda la creazione dei diversi topic;
3) Aggiunta di alcune modifiche riguardo le classi create per renderli adattabili agli altri microservizi;
4) Eliminazione del microservizio fake_producer in quanto utilizzato in una fase di testing precedente per il funzionamento della generazione e consumazione dei messaggi su Kafka;
5) Abilitazione dell'autenticazione dell'utente riguardo mongoDB.

Quindi, in conclusione, le modifiche effettuate riguardano soltanto una piccolissima parte del microservizio utili per l'adattamento.

## Avvio del microservizio

Al fine di eseguire il singolo microservizio e verificarne il funzionamento, è necessario effettuare un flusso di comandi per abilitare l'autenticazione riguardo mongo:
1) Avviare i container mediante il comando:
  docker-compose up
2) Mediante il seguente comando si accede al container:
  docker exec -t id_container_mongo bash
3) Avviare la mongo shell mediante il comando:
  mongo
4) Entrare nel database admin:
  use admin
5) Creare il nuovo utente:
  db.createUser({
  user: 'root',
  pwd: 'toor',
  roles: [{ role: 'readWrite', db:'admin'}]
  }).
  
Successivamente il tutto risulterà pronto al testing.
  
Se invece si vuole testare il microservizio mediante l'utilizzo di Kubernetes:
1) Avviare minikube:
  minikube start
2) Abilitare l'ingress mediante il comando:
  minikube addons enable ingress
3) Creare i diversi pod/service/ingress/volumes mediante il comando:
  kubectl apply -f k8s
4) Creare l'host al fine di abilitare le richieste:
  echo "$(minikube ip) clustera.dsbd2021.it" | sudo tee -a /etc/hosts.
  
Successivamente il tutto risulterà pronto al testing.
