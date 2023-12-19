# Grafo
# SuperGrafo

Este proyecto implementa varias operaciones y algoritmos comunes en grafos, como la búsqueda en profundidad (DFS), la búsqueda en anchura (BFS), el algoritmo de Prim para encontrar el árbol de expansión mínima y el algoritmo de Kruskal para encontrar el árbol de expansión mínima.

## Estructura del Proyecto

El proyecto tiene la siguiente estructura de directorios:

```
build/
	src/
		supergrafo/
			Arista.java
			Grafo.java
			Kruskal.java
			PRIM.java
			SuperGrafo.java
			Vertice.java
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

- [`Grafo`](command:_github.copilot.openSymbolInFile?%5B%22src%2Fsupergrafo%2FGrafo.java%22%2C%22Grafo%22%5D "src/supergrafo/Grafo.java"): Esta es la clase principal que representa un grafo. Proporciona métodos para agregar vértices y aristas al grafo, y para ejecutar algoritmos como DFS, BFS, Prim y Kruskal.

- `Arista`, `Vertice`: Estas clases representan una arista y un vértice en el grafo, respectivamente.

- `Kruskal`: Esta clase implementa el algoritmo de Kruskal para encontrar el árbol de expansión mínima de un grafo.

- `PRIM`: Esta clase implementa el algoritmo de Prim para encontrar el árbol de expansión mínima de un grafo.

## Contribuir

Las contribuciones son bienvenidas. Por favor, abre un problema para discutir lo que te gustaría cambiar o añadir.

## Licencia

Este proyecto está licenciado bajo los términos de la licencia MIT.
