# Projet de Banque Digitale

Ce projet est une application web pour la gestion des clients et de leurs comptes. Il offre diverses fonctionnalités telles que les opérations CRUD des clients, la visualisation des comptes et des opérations, et l'exécution de transactions financières.

## Technologies Utilisées

- Backend : Spring Boot
- Frontend : Angular
- Base de données : MySQL

## Fonctionnalités

- Authentification et autorisation des utilisateurs
- Gestion des clients (Créer, Lire, Mettre à jour, Supprimer)
- Gestion des comptes (Recherche)
- Opérations sur les comptes (Débit, Crédit, Transfert)
- Fonctionnalité de recherche
- Interface utilisateur réactive
## Diagramme de classe
<img src="Captures/class%20Diagramm.png" alt="class diag">

## Architecture de l'application
<img src="Captures/arch%20Diagramm.png" alt="arch">

## Backend

Le backend du projet de banque digitale est développé en utilisant le framework Spring Boot. Il fournit les API nécessaires pour la gestion des clients, des comptes et des opérations.

### Points de Terminaison API

- `/customers` : méthode GET : Récupère tous les clients.
- `/searchCustomers` : Recherche les clients par nom ou email.
- `/customers/{id}` : méthode GET : Récupère un client spécifique par ID.
- `/customers` : méthode POST : Crée un nouveau client.
- `/customers/{id}` : méthode PUT : Met à jour un client existant.
- `/customers/{id}` : méthode DELETE : Supprime un client.
- `/bank-accounts` : méthode GET : Récupère tous les comptes.
- `/bank-accounts/{id}` : méthode GET : Récupère un compte spécifique par ID.
- `bank-accounts/{id}/history` : méthode GET : Récupère l'historique des transactions d'un compte.
- `/bank-accounts/{id}/account-history` : méthode GET : Récupère l'historique d'un compte avec pagination et taille.
- `/bank-accounts/{id}/debit` : méthode POST : Débite un compte.
- `/bank-accounts/{id}/credit` : méthode POST : Crédite un compte.
- `/bank-accounts/{id}/transfer` : méthode POST : Transfère de l'argent entre des comptes.
- `/bank-accounts/customer/{customerId}` : méthode GET : Récupère tous les comptes d'un client.

## Frontend

Le frontend du projet de banque digitale est développé en utilisant le framework Angular. Il fournit une interface utilisateur intuitive pour interagir avec l'application.

### Routes

- `/login` : Page de connexion.
- `/admin/customers` : Page de la liste des clients.
- `/admin/new-customer` : Ajouter un client à la liste.
- `/admin/accounts` : Page de la liste des comptes.
- `/admin/customer-accounts/:customerId` : Liste des comptes d'un client.
- `/admin/not-authorized` : Page de non-autorisation.

### Démo
[![Watch the video](https://github.com/NouhailaAbdtouirsi/ebanking-backend/blob/master/Captures/thumbnail.png)](https://github.com/NouhailaAbdtouirsi/ebanking-backend/blob/master/Captures/Demo.mp4)
## Prise en Main

Pour exécuter le projet de banque digitale localement, suivez ces étapes :

1. Clonez le dépôt.
2. Configurez le backend :
   - Installez Java et Maven.
   - Configurez la connexion à la base de données MySQL dans le fichier `application.properties`.
   - Construisez et exécutez l'application Spring Boot.
3. Configurez le frontend :
   - Installez Node.js et npm.
   - Installez Angular CLI globalement.
   - Installez les dépendances du projet en utilisant `npm install`.
   - Configurez le point de terminaison de l'API backend dans le fichier d'environnement.
   - Construisez et exécutez l'application Angular en utilisant `ng serve`.
4. Accédez à l'application dans votre navigateur à l'adresse `http://localhost:4200`.
