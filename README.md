# Grafo

Este proyecto implementa varias operaciones y algoritmos comunes en grafos, como la búsqueda en profundidad (DFS), la búsqueda en anchura (BFS), el algoritmo de Prim para encontrar el árbol de expansión mínima y el algoritmo de Kruskal para encontrar el árbol de expansión mínima.

## Estructura del Proyecto

El proyecto tiene la siguiente estructura de directorios:

```
build/
 src/
  Docs_Supearbol/
   Consejos de uso.txt
  superarbol/
   Arbol.java
   Arbolo.java
   experimental.java
   Main.java
   Nodo.java
  supergrafo/
   Arista.java
   Grafo.java
   Kruskal.java
   Vertice.java
build.xml
manifest.mf
nbproject/
 README.md
```

## Cómo Compilar y Ejecutar

Este proyecto utiliza Ant para la compilación y la ejecución. Para compilar el proyecto, navega hasta el directorio raíz del proyecto y ejecuta el siguiente comando:

```sh
ant compile
```

Para ejecutar el proyecto, utiliza el siguiente comando:

```sh
ant run
```

## Clases Principales

- [`Grafo`](src/supergrafo/Grafo.java): Esta es la clase principal que representa un grafo. Proporciona métodos para agregar vértices y aristas al grafo, y para ejecutar algoritmos como DFS, BFS, Prim y Kruskal.
- [`Arista`](src/supergrafo/Arista.java): Esta clase representa una arista en el grafo.
- [`Vertice`](src/supergrafo/Vertice.java): Esta clase representa un vértice en el grafo.
- [`Kruskal`](src/supergrafo/Kruskal.java): Esta clase implementa el algoritmo de Kruskal para encontrar el árbol de expansión mínima de un grafo.
- [`Arbol`](src/superarbol/Arbol.java): Esta clase representa un árbol y proporciona métodos para balancear e imprimir el árbol.
- [`Nodo`](src/superarbol/Nodo.java): Esta clase representa un nodo en el árbol.

## Ejemplos de Uso

### Crear un Grafo

```java
Grafo grafo = new Grafo();
Vertice v1 = new Vertice("A");
Vertice v2 = new Vertice("B");
grafo.agregarVertice(v1);
grafo.agregarVertice(v2);
grafo.conectarVertice(v1, v2, 10);
grafo.imprimir_grafo();
```

### Ejecutar Algoritmo de Kruskal

```java
Grafo arbolExpansionMinima = grafo.kruscal();
arbolExpansionMinima.imprimir_grafo();
```

### Crear un Árbol y Balancearlo

```java
Arbol arbol = new Arbol();
arbol.insertar(10);
arbol.insertar(5);
arbol.insertar(15);
arbol.balanceararbol().TreePrinter();
```

## Contribuir

Las contribuciones son bienvenidas. Por favor, abre un problema para discutir lo que te gustaría cambiar o añadir.

## Licencia

Este proyecto está licenciado bajo los términos de la licencia MIT.
