# Documentation de l'Architecture et de la Configuration du Cluster Redis

## Architecture Choisie
Pour la mise en place du cluster Redis
- six instances de Redis fonctionnant en mode cluster. Permet une mise à l'échelle horizontale et une haute disponibilité.

## Composition du Cluster :

3 nœuds maîtres (masters) : Chacun gérant une portion des slots de hachage (16384 slots au total, divisés entre les maîtres).
3 nœuds répliques (slaves) : Chaque réplique est associée à un maître spécifique pour assurer la redondance des données.

## Réseau Docker personnalisé : 
Un réseau en mode bridge est utilisé pour faciliter la communication inter-conteneurs.

- Création du fichier docker-compose.yml :

Définition de six services Redis dans le fichier docker-compose.yml, configurés pour le mode cluster.

Attribution des ports et spécification des commandes de démarrage pour chaque instance.

## Démarrage des Conteneurs :

Utilisation de docker-compose up -d pour lancer les instances Redis en arrière-plan.

Création du Cluster Redis;

Connexion à une instance Redis via docker exec.
Exécution de redis-cli --cluster create avec les adresses IP des conteneurs et spécification de l'option `--cluster-replicas 1` pour configurer les répliques.

Démarrer le Cluster Redis,
## Lancer les conteneurs
`docker-compose up -d`

## Vérifier le statut des conteneurs
`docker-compose ps`

## Créer le cluster
`docker exec -it redis-1 redis-cli --cluster create 172.18.0.7:6379 172.18.0.4:6379 172.18.0.2:6379 172.18.0.6:6379 172.18.0.3:6379 172.18.0.5:6379 --cluster-replicas 1`


# PARTIE 2 : Utilisation du Cluster Redis

**Ouvrir un terminal** : 
**Se connecter à une instance Redis** : 

`docker exec -it src-redis-1-1 redis-cli -c`
Dans cet exemple, src-redis-1-1 est le nom du conteneur Docker pour une des instances Redis. 

Une fois connecté, exemples de Commandes : 
Pour stocker une chaîne de caractères (String) :
`SET greeting "Hello, Redis!"`
Pour récupérer cette chaîne de caractères :
`GET greeting`

**Pour ajouter des éléments à une liste :**
`RPUSH mylist "element1" "element2" "element3"`
Pour récupérer les éléments de la liste :
`LRANGE mylist 0 -1`
Quitter redis-cli
`exit` ou `CTRL+C`.

- Pour les sets : `SMEMBERS key`
- Pour les hashes : `HGETALL key`
- Pour les sorted sets : `ZRANGE key start stop`


# Intégration de Redis dans un Projet

## Partie 3: Utilisation de Redis comme Système de Données et de Cache

### Objectifs
- Utiliser Redis comme entrepôt de données.
- Utiliser Redis comme système de cache dans une application.

### Étapes Réalisées

#### Projet d'Annuaire
- **Création d'une application d'annuaire** : Une application simple où les données de contact sont stockées dans Redis. Ce système permet de récupérer rapidement les informations grâce à Redis.
- **Développement d'un script de mesure web** : Un script a été développé pour mesurer la taille des pages web. Les résultats sont stockés dans Redis avec une durée de vie définie, utilisant Redis comme cache pour éviter des mesures répétitives.

### Restitution
- **Bénéfices observés** : Amélioration significative de la performance et de la scalabilité.

## Partie 4: Introspection sur l'Intégration de Redis

### Objectifs
- Analyser l'impact potentiel de l'intégration de Redis dans des projets futurs.

### Étapes Réalisées

#### Évaluation des Projets Actuels
- Points foorts Redis :  performance et de scalabilité.
- Redis, en tant que magasin de données en mémoire rapide, peut être utilisé dans une multitude de projets et d'applications, offrant des performances élevées et diverses fonctionnalités telles que la gestion des structures de données en mémoire, la persistance des données, la publication et l'abonnement aux messages, et plus. Voici quelques exemples  :

1. Systèmes de Gestion de Sessions

2. Caching de Données

3. File d'attente de Messages

4. Stockage de Données en Temps Réel
  

### Restitution
- Une analyse détaillée a été préparée sur les avantages de l'intégration de Redis dans différents types de projets. Les résultats montrent que Redis peut significativement améliorer la performance des applications en réduisant le temps de réponse et en augmentant la capacité à monter en charge.

