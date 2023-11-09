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

***Formato de entrega de códigos:***

CodeHunter está pensado para trabajar con una carpeta con archivos .zip con un nombre con el formato similar a lo que se presenta (Figura 1).

![Formato de entrega de códigos](https://github.com/Pollux02/CodeHunter/assets/62532201/56378e28-1ef3-405f-8695-5ebc50021c4c) *Figura 1.1: Formato de entrega de códigos.*

j3: Id del entregable (2 caracteres).

pro1: Asignatura para la que se entrega el código (4 caracteres).

023b: Calendario en el que se entrega el código. En este caso es en el segundo semestre de 2023, por ende es 2023b (4 caracteres).

Lo demás será el nickname del estudiante. El alumno tiene permitido enviar una o más versiones extra en caso de error, **CodeHunter** es capáz de detectar esos casos y restar el puntaje indicado.

Dentro del ".zip" puede haber n carpetas pero siempre debe existir una carpeta con el nombre "C". Si no se encuentra esta carpeta **CodeHunter** tomará como inválido el entregable y la calificación sera de 5/100.

Dentro de la carpeta "C" debe tener el "main.c", en caso de que no se encuentre ya sea por que tiene otro nombre o está dentro de otra carpeta **CodeHunter** tomará como inválido el entregable y la calificación sera de 5/100.


***Formato de pruebas:***

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

En caso de que no se requiera un mensaje específico basta con poner:

Ash

\=

Ash

\*

De este modo **CodeHunter** buscará sólo el nombre ingresado en la salida del programa, por ende, si un programa que está siendo evaluado imprime "Hola Ash, gusto en conocerte" **CodeHunter** dará por válido ese programa ya que tiene la subcadena "Ash" en su interior.

En las pruebas también es posible fijar un orden en las salidas, esto se hace colocando O (orden) después del signo '='.
Por ejemplo si se solicita imprimir la tabla de multiplicación de un número en orden ascendente sería de la siguiente forma:

2

\=O

2

4

6

8

10

\*

En este caso **CodeHunter** buscará en la salida los números [2, 4, 6, 8, 10] en el orden descrito, por lo tanto si un programa tiene como salida [10, 8, 6, 4, 2], será un código inválido.

Si se espera una salida numérica cualquiera, sin importar cual sea esta, se puede colocar en la prueba '\d':

\=

\d

\*

En esta prueba no hay entradas pero si se espera una salida numérica cualquiera.

## Demostración

Se ingresa a **CodeHunter** la ruta de la carpeta con todos los archivos zip de los alumnos. Al presionar el botón "Seleccionar carpeta" se extraerán todos los archivos zip y se buscará la carpeta "C" en el interior de cada carpeta de alumno. En este caso la ruta a examinar será "/home/ash/Documentos/CUCEI/ServicioSocial/PruebasJuez/Version1.0/J3"

![Ingreso de la ruta con los archivos ".zip"](https://github.com/Pollux02/CodeHunter/assets/62532201/52ec8fa5-1186-445a-b5c7-a4f3eef44e9c) *Figura 2.1: Ingreso de la ruta con los archivos ".zip".*

Al terminar el proceso de extracción, si verificamos la carperta ingresada en **CodeHunter** podremos ver que se extrajo cada ".zip" y se creó una carpeta para cada alumno.

![Extracción de los archivos ".zip"](https://github.com/Pollux02/CodeHunter/assets/62532201/7b5329f2-ba44-4bc4-9ff0-3cfa47eb06ba) *Figura 2.2: Extracción de los archivos ".zip".*

En este momento aparece el botón "Seleccionar archivo de pruebas".

![Botón "Seleccionar archivo de pruebas"](https://github.com/Pollux02/CodeHunter/assets/62532201/e0a4a9b1-8756-49f7-8cef-5ec529fe433a) *Figura 2.3: Botón "Seleccionar archivo de pruebas".*

Al dar click en este botón se abrirá una ventana para seleccionar el ".txt" de prueba.

![Selección de archivo de pruebas](https://github.com/Pollux02/CodeHunter/assets/62532201/208f4353-2f87-42db-aff8-53a7d55d028e) *Figura 2.4: Selección de archivo de pruebas.*

Al elegir el archivo de pruebas **CodeHunter** comenzará con la evaluación automática y mostrará los resultados finales.

![Resultados finales](https://github.com/Pollux02/CodeHunter/assets/62532201/8177f54b-b291-411a-b737-f26d48526042) *Figura 2.5: Resultados finales.*

Podemos ver que en la revisión de "TresCinco" podemos ver que se le asignó una calificación de 5/100 ya que no se encontró "main.c". Al revisar su carpeta "C", por algún error del alumno, se guardo el código fuente con el nombre "main.c.c".

![Carpeta de "TresCinco"](https://github.com/Pollux02/CodeHunter/assets/62532201/043ed031-3562-4695-ba77-ad31426bc975) *Figura 2.6: Carpeta de "TresCinco".*

Al parecer "CuatroCero" no cumple con lo requerido para el programa. Si revisamos su código podemos ver que no solicita ningún dato al usuario y el cálculo lo hace con valores hardcodeados, pero en el archivo de pruebas se solicitan entradas para el programa.

Archivo de pruebas:

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

Cómo se puede ver en las pruebas hay entradas, pero en el código de "CuatroCero" no se permiten entradas.

![Código de "CuatroCero"](https://github.com/Pollux02/CodeHunter/assets/62532201/3b80b985-e201-4af1-92b8-42dee0959cb0) *Figura 2.7: Código de "CuatroCero".*



## Requisitos

- Java JDK17

## Instalación

- Linux (Ubuntu):

Se descarga el ".deb" de la siguiente página https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.deb
```bash
sudo dpkg -i /ruta/a/la/carpeta/jdk-17_linux-x64_bin.deb

