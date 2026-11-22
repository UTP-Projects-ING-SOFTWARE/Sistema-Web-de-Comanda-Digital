# Guía de ejecución: Liverpool Restobar (Windows)

**Sistema Web de Comanda Digital**

Esta guía describe los pasos necesarios para ejecutar el proyecto en un entorno Windows utilizando un IDE.

---

## Requisitos previos

Antes de comenzar, verifique que tenga instalado:

1. **Java 21 (JDK)**  
   Configurar la variable `JAVA_HOME` durante la instalación.

2. **PostgreSQL 16**  
   Durante la instalación, registre la contraseña asignada al usuario `postgres`.

3. **IDE compatible**  
   IntelliJ IDEA, Eclipse o VS Code.

---

## Paso 1: Preparar la base de datos

1. Abra **pgAdmin** y conéctese al servidor local con el usuario `postgres` y la contraseña definida durante la instalación.

2. Abra una **Query Tool** (Herramienta de consulta).

3. Abra el archivo `init_db.sql`, ubicado en la carpeta `docs/windows/`, copie su contenido, péguelo en la Query Tool y ejecútelo con `F5`.

   Este script crea el usuario `swcd_user`, la base de datos `swcd_db` y asigna los permisos correspondientes.

---

## Paso 2: Configurar el proyecto

1. Abra el proyecto en el IDE mediante `File -> Open` y seleccione la carpeta raíz del proyecto.

2. Espere a que Maven descargue automáticamente todas las dependencias.

3. Abra el archivo:

   ```text
   src/main/resources/application.properties
   ```

4. Modifique el puerto de conexión a la base de datos. El proyecto original está configurado para el puerto `5433`; en Windows, PostgreSQL utiliza por defecto el puerto `5432`.

   Busque la línea:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/swcd_db
   ```

   Y reemplácela por:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/swcd_db
   ```

5. Verifique que el usuario y la contraseña configurados sean los correctos:

   ```properties
   spring.datasource.username=swcd_user
   spring.datasource.password=swcd_pass
   ```

---

## Paso 3: Ejecutar el proyecto

1. En el IDE, navegue hasta la clase principal:

   ```text
   src/main/java/boot/RestobarApp.java
   ```

2. Haga clic derecho sobre el archivo y seleccione:

   ```text
   Run 'RestobarApp.main()'
   ```

3. Espere hasta que la consola muestre un mensaje similar a:

   ```text
   Started RestobarApp in X seconds
   ```

4. Abra un navegador web e ingrese a:

   ```text
   http://localhost:8080
   ```

---

## Credenciales de acceso

La primera vez que se inicie el proyecto, el sistema creará automáticamente un usuario administrador con las siguientes credenciales:

| Campo | Valor |
|---|---|
| Usuario | `admin` |
| Contraseña | `admin123` |
