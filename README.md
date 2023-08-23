## Description

La classe `MainActivity` du package `fr.jnda.android.dexloading.sample` est conçue pour charger et exécuter du code depuis un fichier DEX (Dalvik Executable), soit à partir des assets de l'application, soit directement depuis une URL, puis l'exécuter.

## Fonctionnalités

1. **Charger un fichier DEX depuis les assets** : Le fichier est copié des assets vers le cache de l'application avant d'être chargé.

2. **Charger un fichier DEX depuis une URL** : Le fichier est téléchargé depuis une URL GitHub, chargé en mémoire sans être écrit sur le disque, puis exécuté.

3. **Exécuter du code depuis le fichier DEX** : Une fois le fichier DEX chargé, la classe tente d'extraire et d'exécuter une méthode spécifique depuis celui-ci.

## Comment ça fonctionne

- **Chargement depuis les assets** :
    - La méthode `loadDex` se charge de la lecture du fichier DEX depuis les assets de l'application et de son écriture dans le cache de l'application. Elle utilise ensuite le `DexClassLoader` pour charger le fichier DEX.

- **Chargement depuis une URL** :
    - La méthode `loadInMemory` télécharge le fichier DEX depuis une URL spécifique (`githubUrlDex`) à l'aide de la fonction `downloadFileToByteBuffer`. Le fichier est chargé directement en mémoire sans être écrit sur le disque. Pour ce faire, la classe utilise le `InMemoryDexClassLoader`.

- **Exécution du code DEX** :
    - Les méthodes `executeDex` et `executeInMemory` sont responsables de l'exécution du code depuis le fichier DEX chargé. Elles tentent de charger une classe spécifique (`fr.jnda.android.dexloading.payload.StringValue`) et d'invoquer une méthode (`generateRandomString`) de cette classe. Le résultat est ensuite affiché à l'utilisateur via un `Snackbar`.

## Utilisation

Lorsque l'application est lancée, l'utilisateur est présenté avec une interface contenant quatre boutons :

1. **Charger DEX depuis les assets** : Lorsqu'appuyé, cela déclenche la méthode `loadDex`.

2. **Charger DEX depuis la mémoire** : Lorsqu'appuyé, cela déclenche la méthode `loadInMemory`.

3. **Exécuter le code DEX depuis le fichier** : Lorsqu'appuyé, cela déclenche la méthode `executeDex`.

4. **Exécuter le code DEX depuis la mémoire** : Lorsqu'appuyé, cela déclenche la méthode `executeInMemory`.

Des messages de réussite ou d'erreur sont affichés à l'utilisateur via des `Snackbar`.

## Note

Ce code est simpliste ne gère pas ou peu les erreurs et est proposé dans un but de démonstration
L'utilisation de chargement dynamique de code, en particulier depuis des sources non fiables, peut poser des risques en matière de sécurité. Assurez-vous de bien comprendre ces risques et de prendre des mesures appropriées pour garantir la sécurité de votre application et de vos utilisateurs.