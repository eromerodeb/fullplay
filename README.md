
# Puntos Extras

1.   Para salvaguardar el caso en que un **producto** se vendan al mismo tiempo una solucion para el caso en que se quisiera evitar la venta del
 mismo **producto** simultaneamente seria la implementacion de un **hilo** que se encargaria de realizar todas las ventas, asi se aseguraria la venta,
 esto pudiera ser factible en sistemas con pocas solicitudes de ventas pues en el caso que fuera un sistema que realice n cantidad de solicitudes mayor igual 
 a 10000 simultaneas entonces implicaria un retardo que pudiera llegar a ser considerable en dependencia de la carga del sistema. A una 
 solucion un poco mas elaborada se podria llegar haciendo uso de **semafaros** e **hilos** no sobre los **productos** sino sobre los **insumos** que son los 
 que limitan las ventas, se pudieran crean n **hilos** que compartan la misma cola de pedidos de ventas, tomen la solicitud miren por los **insumos** 
 de estos y bloquen los mismos y una vez finalizada la venta los desbloqueen, esto implicaria una carga adicional para el sistema en cuanto a 
 procesamiento de informacion pero a la larga y haciendo un correcto uso de los **semafaros** y los **hilos** garantiza una tiempo mayor de respuestas
 frente a grandes solicitudes de ventas, ya que dos **productos** no necesariamente tienen que compartir los **insumos**.
2.   En el caso de la cancelacion se tendria que tener en cuenta cuan frequente se modifican los productos, puesto que esto podria implicar
 que una vez realizada la venta y se quiera cancelar la misma en el intervalo en que se vende y se cancela el **producto** puede sufrir cambios por
 lo que simplemente no se puede tomar el **producto** ver cuales **insumos** tiene y restaurarlos pues estos pueden haber cambiados, una solucion seria 
 la serializacion del **producto** y de los **insumos** del producto, y a la hora de realizar la cancelacion entonces deserializarlos e ir comprobando 
 la existencia de los mismos en caso de existir solo se les actualiza la cantidad, en caso de que se eliminaran ya se tienen los datos necesarios 
 para su creacion tanto del **producto** como de los **insumos** de este, tener en cuenta que al tener varias ventas activas entonces la BD puede creser 
 considerablemente debido a la serializacion de los **productos** con los **insumos**, por lo que se pudieran tener estos datos en otra tabla relacionada
 con la tabla de la venta y una vez finalizada la misma se puede ir eliminado las entradas de esta tabla de serializaciones.
3.   Para la realizacion de un indicador en rojo cuando se agoto un **insumo** se puede hacer uso de `ng-class` y establecer una clase, 
 esto seria en el caso mas sencillo, si ya se desea que sea mas generico y que pueda ser usado en varios lugares sin atarlo a los **insumos**, 
 se puede considerar la creacion de una directiva que se encargue de hacer las comprobaciones necesarias.
4.   Entre los test que se le pudieran a hacer a esta aplicacion web se encuentran tanto las pruebas de caja blanca como las pruebas de caja negra,
 pruebas unitarias, pruebas de disponibilidad y estres para medir el comportamiento del sistema antes grandes cargas de peticiones (en este 
 caso no creo que obtenga buenos resultados en las de estres :D :D) ,.
5.   Respecto a este punto un ejemplo basico literalmente seria lo implementado en el **branch** `security` (user:admin, pass:admin), ya de querer 
 algo mas robusto se tendria que adicionar a los usuarios roles, y en base a los roles garantizar los permisos a los recursos, entidades o datos 
 del sistema. 
6.   Para la correcta distribucion de los assets se puede hacer uso de ***bower*** y homologos de este mismo tipo de distribuicion de assets 
 existentes en la actualidad.

## NOTES

Por alguna razon que desconosco me retorna -1 cuando deberia retornar 0 el siguiente codigo:

```javascript
    var items = [3,4,6,8], id = 3;
    var index = items.findIndex(function(item, index){
        if (item == id) {
            return index;
        }
        return false;
    });
    console.info("Index is: " + index);
```

La razon asumo que es: mientras sea falso seguir iterando, al ser 0 equivalente a `false` entonces gana logica el comportamiento.
A raiz del mismo realice la siguiente modificacion:

```javascript
    var index = -1, items = [3,4,6,8], id = 3;
    items.findIndex(function(item, i){
        if (item == id) {
            index = i;
            return true;
        }
        return false;
    });
    console.info("Index is: " + index);
```
