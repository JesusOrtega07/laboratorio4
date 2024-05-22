-- create de la tabla CursosEspeciales 
create table CursosEspeciales(
idcurso int,
idprofe int,
fini varchar,
ffin varchar,
ncuenta int,
cns int,
foreign key(idprofe) references Profesores,
primary key (idcurso)
);

--Cursos impartidos por los docentes: Julio y Samuel
insert into CursosEspeciales values(10,5,20080204,20080204, 0, 0);
insert into CursosEspeciales values(20,6,20080204,20080204, 0, 0);

-- Creación de la función AltaDeCheque
DROP FUNCTION AltaDeCheque(int, int, numeric, int);
CREATE OR REPLACE FUNCTION AltaDeCheque(int, int, numeric, int ) RETURNS int AS '
DECLARE
vcta alias for $1;
vnchq alias for $2;
vcant alias for $3;
vcur alias for $4;
vreg record;
vnuevo numeric;
vban int;
BEGIN
vban = 0;
-- Selecciona la cuenta y decrementa el saldo
Select into vreg * from CuentaCheques where ncuenta =
vcta for update;
vnuevo := vreg.saldo - vcant;
update CuentaCheques set saldo = vnuevo where
ncuenta = vcta;
-- Inserta el nuevo cheque
Insert into Cheque values (vcta, vnchq, vcant, now() );
-- Relaciona el cheque con el curso
Update CursosEspeciales set ncuenta=vcta, cns = vnchq
where idcurso = vcur;
vban = 1;
return vban;
END;
' LANGUAGE 'plpgsql';

-- Consulta de cuentas para verificar el saldo actual de la cuenta no. 2 
select * from CuentaCheques;

-- Consultando los cursos especiales
-- Es importante recalcar los cursos impartidos por cada docente
-- Julio imparte el curso con id: 10 llamado "diseño" con un pago de 3000
-- Samuel imparte el curso con id: 20 llamado "java" con un pago de 1000 
Select * from CursosEspeciales;

-- Comprobando el funcionamiento de AltaDeCheque
Select AltaDeCheque( 2, 21, 3000, 10);
Select AltaDeCheque( 2, 22, 1000, 20);

-- Consulta de la cuenta de cheques para verificar el cobro
-- El resultado deseado en la acuenta no.2 es de 5000, recordando que tenía 9000 y fue cobrado 4000
select * from CuentaCheques;

-- Retornando todos los cambios a sus valores originales antes de todas las pruebas
Update CursosEspeciales set ncuenta=0, cns = 0 where idcurso= 10;
Update CursosEspeciales set ncuenta=0, cns = 0 where idcurso= 20;
Delete from Cheque where ncuenta = 2;
Update CuentaCheques set saldo = 9000 where ncuenta = 2;


----- TRABAJO ADICIONAL -----
CREATE OR REPLACE FUNCTION AltaDeCheque(int, int, numeric, int) RETURNS int AS ' 
DECLARE
vcta alias for $1;
vnchq alias for $2;
vcant alias for $3;
vcur alias for $4;
vreg record;
vnuevo numeric(10,2);
vban int; BEGIN vban = 0;
-- Selecciona la cuenta
Select * into vreg from CuentaCheques where ncuenta = vcta; i
if vreg.saldo >= vcant and vnuevo >= 0 then vnuevo := vreg.saldo - vcant;
update CuentaCheques set saldo = vnuevo where ncuenta = vcta;
-- Inserta el nuevo cheque
Insert into Cheque values (vcta, vnchq, vcant, now());
-- Relaciona el cheque con el curso
Update CursosEspeciales set ncuenta = vcta, cns = vnchq where idcurso = vcur; vban = 1;
else raise exception
''No se puede retirar más dinero del que tiene la cuenta o el saldo resultante sería negativo'';
end if; return vban; END; '
LANGUAGE 'plpgsql';