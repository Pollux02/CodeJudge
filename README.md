# CodeHunter

Juez creado para hacer una revisión de códigos automática.

## Tabla de Contenidos

- [Manual](#manual)
- [Demostración](#demostración)
- [Requisitos](#requisitos)
- [Instalación](#instalación)

## Manual
**Algunas funciones del programa se hicieron pensadas para la forma en que trabaja el profesor.**

**Versión 1.0:**

**Formato de entrega de códigos:**

CodeHunter está pensado para trabajar con una carpeta con archivos .zip con un nombre con el formato similar a lo que se presenta (Figura 1).

![Formato de entrega de códigos](https://github.com/Pollux02/CodeHunter/assets/62532201/56378e28-1ef3-405f-8695-5ebc50021c4c)

Figura 1: Formato de entrega de códigos.

j3: Id del entregable (2 caracteres).

pro1: Asignatura para la que se entrega el código (4 caracteres).

023b: Calendario en el que se entrega el código. En este caso es en el segundo semestre de 2023, por ende es 2023b (4 caracteres).

Lo demás será el nickname del estudiante. El alumno tiene permitido enviar una o más versiones extra en caso de error, **CodeHunter** es capáz de detectar esos casos y restar el puntaje indicado.


**Formato de pruebas:**

Las pruebas para cada programa se generan por medio de un ".txt" el cual contendrá las entradas que se ingresarán a los programas a evaluar y las salidas esperadas.

Ejemplo de prueba.txt:

2

2

\=

2

\*

4

4

\=

8

\*

7

2

\=

7

\*

Cada prueba se separará con un '*'.
Todo lo que esté antes del signo '=' serán entradas y todo lo se esté después serán salidad esperadas.

Cada entrada y cada salida debe estar separado por un salto de linea y cada linea no puede tener más de una palabra o número como se puede ver en este caso.

Por ejemplo, si se solicita un programa que pida su nombre al usuario y lo salude con un "Hola (nombre), ¿cómo estás?". El archivo de prueba debe verse así:

Ash

\=

Hola

Ash,

¿cómo

estás?

\*

## Demostración

Se ingresa a **CodeHunter** la ruta de la carpeta con todos los archivos zip de los alumnos. Al presionar el botón "Seleccionar
carpeta" se extraerán todos los archivos zip y se buscará la carpeta "C" en el interior de cada carpeta de alumno.

![image](https://github.com/Pollux02/CodeJudge/assets/62532201/ea373083-3a63-45a2-947f-4d1ba1eab850)

## Requisitos

Enumera los requisitos necesarios para utilizar tu proyecto, como software, bibliotecas, etc.

## Instalación

Proporciona instrucciones claras y concisas sobre cómo instalar tu proyecto.

```bash
comando de instalación

