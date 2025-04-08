# ️ Application E-Commerce – React.js & Spring Boot

Cette application est une plateforme de commerce en ligne complète développée avec React.js pour le frontend et Spring Boot pour le backend. Elle permet aux utilisateurs de parcourir les produits, d'ajouter au panier, d'acheter via Stripe, et aux administrateurs de gérer les produits et catégories.

---

##  Pile Technologique

### Frontend
- React.js
- Bootstrap 5
- React Router DOM
- Axios
- Stripe.js et React Stripe.js
- Formik & Yup (validation de formulaires)

### Backend
- Spring Boot
- Spring Security (authentification basée sur les sessions)
- Spring Data JPA (MySQL)
- Stripe Java SDK
- Lombok
- Maven
- Spring Boot DevTools
- Spring Boot Actuator (monitoring)
- Spring Mail (notifications par email)

---

## Détail des Dépendances

### 🔹 Frontend (React.js)

| Package                | Utilisation                                                                 |
|------------------------|-----------------------------------------------------------------------------|
| `react`, `react-dom`   | Librairie de base React pour construire l'interface utilisateur             |
| `axios`                | Pour effectuer les requêtes HTTP vers l'API Spring Boot                     |
| `bootstrap`            | Framework CSS pour un design responsive                                     |
| `@reduxjs/toolkit`     | Gestion de l'état global et logique métier                                  |
| `react-redux`          | Connecter les composants React au store Redux                               |
| `classnames`           | Pour manipuler dynamiquement les classes CSS                                |
| `react-router-dom`     | Navigation entre les pages (produits, panier, admin...)                     |
| `framer-motion`        | Bibliothèque d'animations React                                             |
| `react-icons`          | Icônes vectorielles modernes                                                |
| `react-toastify`       | Notifications utilisateur élégantes                                         |
| `@stripe/stripe-js`    | Librairie Stripe sécurisée côté client                                      |
| `@stripe/react-stripe-js` | Composants React pour Stripe                                             |

### 🔹 Backend (Spring Boot)

| Dépendance                              | Description                                                       |
|----------------------------------------|-------------------------------------------------------------------|
| `spring-boot-starter-web`              | Pour créer des services RESTful                                   |
| `spring-boot-starter-security`         | Sécurité (connexion, autorisation des routes)                     |
| `spring-boot-starter-data-jpa`         | ORM avec Hibernate                                                |
| `spring-boot-starter-validation`       | Validation des entités et DTO                                     |
| `spring-boot-starter-mail`             | Service d'envoi d'emails                                          |
| `spring-boot-starter-actuator`         | Surveillance et métriques de l'application                        |
| `spring-boot-devtools`                 | Outils de développement (redémarrage auto)                        |
| `mysql-connector-java`                 | Connexion à MySQL                                                 |
| `stripe-java`                          | Intégration Stripe pour les paiements                             |
| `lombok`                               | Réduction du boilerplate (getters/setters automatiques)           |
| `springdoc-openapi-ui`                 | Documentation API avec Swagger                                    |
| `spring-boot-starter-test`             | JUnit et Mockito pour les tests                                   |
| `spring-security-test`                 | Tests d'intégration avec la sécurité                             |

---

##  Flux d'API Frontend ↔ Backend

### Authentification et Utilisateurs

| Fonctionnalité         | Méthode HTTP | Composant React        | Endpoint Backend                   | Description                              |
|------------------------|--------------|------------------------|-----------------------------------|------------------------------------------|
| Connexion utilisateur  | `POST`       | `Authentication/Login.jsx` | `/api/account/login`           | Authentifie l'utilisateur via session    |
| Déconnexion            | `POST`       | `Authentication/Logout.jsx` | `/api/account/logout`         | Supprime la session                      |
| Inscription            | `POST`       | `Authentication/Register.jsx` | `/api/account/register`     | Crée un nouvel utilisateur               |

### Produits et Catalogue

| Fonctionnalité         | Méthode HTTP | Composant React        | Endpoint Backend                  | Description                              |
|------------------------|--------------|------------------------|----------------------------------|------------------------------------------|
| Voir produits          | `GET`        | `shopPage/ProductGrid.jsx` | `/api/products`              | Liste paginée des produits               |
| Rechercher produits    | `GET`        | `common/SearchBar.jsx` | `/api/products/search?name={query}` | Recherche textuelle                   |
| Détails produit        | `GET`        | `ProductDetail.jsx`    | `/api/products/{id}`             | Informations complètes du produit        |
| Catégories             | `GET`        | `sidebar/CategoryList.jsx` | `/api/categories`            | Liste des catégories                     |
| Produits par genre     | `GET`        | `sidebar/GenderFilter.jsx` | `/api/products/gender/{gender}` | Produits filtrés par genre           |

### Panier et Commandes

| Fonctionnalité         | Méthode HTTP | Composant React        | Endpoint Backend                  | Description                              |
|------------------------|--------------|------------------------|----------------------------------|------------------------------------------|
| Créer panier           | `POST`       | `Cart/CartContext.jsx` | `/api/commandes/panier/{utilisateurId}` | Crée un nouveau panier          |
| Ajouter au panier      | `POST`       | `Cart/AddToCart.jsx`   | `/api/commandes/{commandeId}/ajouter-produit` | Ajoute un produit au panier |
| Voir panier            | `GET`        | `Cart/CartPage.jsx`    | `/api/commandes/{id}`            | Affiche le contenu du panier            |
| Passer commande        | `POST`       | `payment/Checkout.jsx` | `/api/commandes/{commandeId}/passer-commande` | Finalise la commande      |
| Paiement Stripe        | `POST`       | `payment/StripeForm.jsx` | `/api/product/v1/checkout`      | Traitement du paiement sécurisé         |
| Voir mes commandes     | `GET`        | `users/OrderHistory.jsx` | `/api/commandes/utilisateur/{utilisateurId}` | Historique des commandes |
| Annuler commande       | `POST`       | `users/OrderDetail.jsx` | `/api/commandes/{commandeId}/annuler` | Annule une commande récente       |

### Administration

| Fonctionnalité            | Méthode HTTP | Composant React        | Endpoint Backend                  | Description                              |
|---------------------------|--------------|------------------------|----------------------------------|------------------------------------------|
| Dashboard statistiques    | `GET`        | `Dashboard/Dashboard.jsx` | `/api/stats`                  | Statistiques ventes et utilisateurs      |
| Liste produits (admin)    | `GET`        | `Dashboard/ProductManagement.jsx` | `/api/products`       | Gestion des produits                     |
| Ajouter produit           | `POST`       | `Dashboard/ProductForm.jsx` | `/api/products`             | Crée un nouveau produit                  |
| Modifier produit          | `PUT`        | `Dashboard/ProductForm.jsx` | `/api/products/{id}`        | Met à jour un produit                    |
| Supprimer produit         | `DELETE`     | `Dashboard/ProductTable.jsx` | `/api/products/{id}`       | Supprime un produit                      |
| Gérer catégories          | `GET/POST/PUT` | `Dashboard/CategoryManagement.jsx` | `/api/categories` | CRUD des catégories                      |
| Gérer commandes           | `GET`        | `Dashboard/OrdersManagement.jsx` | `/api/commandes`       | Traitement des commandes                 |
| Modifier statut commande  | `PUT`        | `Dashboard/OrderDetail.jsx` | `/api/commandes/{commandeId}/statut` | Met à jour le statut d'une commande |

---

##  Structure des Dossiers

### Frontend

```
src/
├── assets/
├── common/
│   ├── Cart/
│   ├── footer/
│   ├── header/
│   └── payment/
├── components/
│   ├── announcement/
│   ├── assets/
│   ├── Authentication/
│   ├── contact/
│   ├── Dashboard/
│   ├── discount/
│   ├── FixedPlugin/
│   ├── flashDeals/
│   ├── MainPage/
│   ├── newarrivals/
│   ├── shopPage/
│   ├── shops/
│   ├── sidebar/
│   ├── top/
│   ├── users/
│   ├── wishlist/
│   └── wrapper/
│   └── Data.js
├── service/
├── pages/
├── App.css
├── App.js
├── index.js
├── .gitignore
├── package-lock.json
└── package.json
```

### Backend

```
src/main/java/com/myecommerce/
├── config/
│   ├── SecurityConfig.java
│   ├── StripeConfig.java
│   └── WebMvcConfig.java
├── controller/
│   ├── AccountController.java
│   ├── CategoryController.java
│   ├── CommandeController.java
│   ├── ProductCheckoutController.java
│   └── ProductController.java
├── DTO/
│   ├── CategoryDTO.java
│   ├── CommandeResponseDTO.java
│   ├── LigneCommandeAjoutDTO.java
│   ├── LigneCommandeResponseDTO.java
│   ├── ProductDTO.java
│   ├── ProductRequestStripe.java
│   ├── RegisterDto.java
│   └── StripeResponse.java
├── exception/
│   └── GlobalExceptionHandler.java
├── model/
│   ├── AppUser.java
│   ├── Category.java
│   ├── Commande.java
│   ├── LigneCommande.java
│   ├── Product.java
│   └── StatutCommande.java
├── repository/
│   ├── CategoryRepository.java
│   ├── CommandeRepository.java
│   ├── LigneCommandeRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
├── security/
│   └── SecurityConfig.java
├── service/
│   ├── CategoryService.java
│   ├── CommandeService.java
│   ├── ProductService.java
│   ├── StripeService.java
│   └── UserService.java
└── EcommerceApplication.java
```

---

##  Fonctionnalités Principales

###  Gestion des Utilisateurs
- Inscription avec validation d'email
- Connexion sécurisée avec JWT ou sessions
- Gestion de profil (adresses, préférences)
- Récupération de mot de passe
- Niveaux d'accès (utilisateur, administrateur)

###  Catalogue de Produits
- Affichage paginé des produits
- Recherche par mot-clé
- Filtrage par catégorie, prix, notation
- Tri par popularité, prix, date
- Système de notation et commentaires
- Images multiples par produit
- Gestion des stocks

###  Panier d'Achat
- Ajout/suppression de produits
- Modification des quantités
- Enregistrement du panier dans la session
- Panier persistant pour utilisateurs connectés
- Application de codes promo

###  Processus de Paiement
- Intégration Stripe sécurisée
- Paiement par carte bancaire

###  Gestion des Commandes
- Suivi des statuts en temps réel
- Historique des commandes
- Annulation de commande (si statut permet)
- Système de retour produit

###  Panneau d'Administration
- Tableau de bord avec statistiques
- Gestion complète des produits et catégories
- Suivi des commandes et mise à jour des statuts
- Gestion des utilisateurs

---

##  Sécurité

- Authentification robuste (sessions sécurisées) 
- Protection CSRF
- Validation des entrées côté serveur et client
- Rôles et permissions fine-grained
- Stockage sécurisé des mots de passe (BCrypt)
- Rate limiting pour prévenir les attaques par force brute

##  Tests

### Backend
- Tests unitaires avec JUnit 5
- Tests d'API avec JUnit et mochito

---

##  Installation et Déploiement

### Prérequis
- Node.js 16+
- Java 17+
- MySQL 8+
- Maven 3.8+

### Installation Frontend
```bash
# Cloner le repository
git clone https://github.com/salahedinerabii/ecomwebsite.git

# Accéder au dossier frontend
cd ecommerce-app/frontend

# Installer les dépendances
npm install

# Créer un fichier .env avec les variables suivantes
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_STRIPE_PUBLIC_KEY=pk_test_51R5sK4FaTQqzl2W9tQuV2BidxI5yLZYCYVNmz6zAMunSMdWkQa5S9GhV5bCn3buKHs6U6h3SKZ2u76UUyN8cLnpI004sYlyHrF

# Démarrer l'application en mode développement
npm start
```

### Installation Backend
```bash
# Accéder au dossier backend
cd ecommerce-app/backend

# Configurer application.properties avec les informations de BDD

# Construire l'application
mvn clean install

# Démarrer le serveur
mvn spring-boot:run
```

### Déploiement en Production
- Frontend: CloudFlare
---

##  Captures d'Écran

*À ajouter après le développement initial*

![HomePage](../ecommerce_readme_bundle/homepage.png)
![HomePage1](../ecommerce_readme_bundle/homepage1.png)
![HomePage2](../ecommerce_readme_bundle/homepage2.png)
![HomePage3](../ecommerce_readme_bundle/homepage3.png)
![RegisterPage](../ecommerce_readme_bundle/registerpage.png)
![LoginPage](../ecommerce_readme_bundle/loginpage.png)
![ShopPage](../ecommerce_readme_bundle/shoppage.png)
![CartPage](../ecommerce_readme_bundle/cartpage.png)

---

##  Diagramme d'Architecture

Voir le fichier joint : `architecture_diagram.png`

Il montre :
1. Frontend React.js avec Redux pour la gestion d'état
2. Communication HTTP/REST via Axios
3. Backend Spring Boot avec couches Controller, Service, Repository
4. Spring Security pour l'authentification et l'autorisation
5. Spring Data JPA pour l'accès aux données
6. Intégration avec Stripe pour les paiements
7. Service d'emailing pour les notifications
8. Base de données MySQL

---

##  Contributeurs

- Salah Eddine Rabii
- Youssef Achtouk
- Raihana Amrani
- Rania Bikiker
- Imane Bouhouche
- Chaymae Serrar
