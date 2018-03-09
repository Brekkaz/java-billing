CREATE table "TIPOUSUARIO" (
    "ID"         NUMBER NOT NULL,
    "NOMBRE"     VARCHAR2(50) NOT NULL,
    constraint  "TIPOUSUARIO_PK" primary key ("ID")
)
/

CREATE sequence "TIPOUSUARIO_SEQ" 
/

CREATE trigger "BI_TIPOUSUARIO"  
  before insert on "TIPOUSUARIO"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "TIPOUSUARIO_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/

-------------------------------------------------------------------
CREATE table "ESTADOUSUARIO" (
    "ID"         NUMBER NOT NULL,
    "NOMBRE"     VARCHAR2(50) NOT NULL,
    constraint  "ESTADOUSUARIO_PK" primary key ("ID")
)
/

CREATE sequence "ESTADOUSUARIO_SEQ" 
/

CREATE trigger "BI_ESTADOUSUARIO"  
  before insert on "ESTADOUSUARIO"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "ESTADOUSUARIO_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/

-------------------------------------------------------------------
CREATE table "USUARIO" (
    "ID"         NUMBER NOT NULL,
    "USUARIO"    VARCHAR2(50) NOT NULL,
    "CONTRASENA" VARCHAR2(50) NOT NULL,
    "NOMBRE"     VARCHAR2(50) NOT NULL,
    "IDTIPO"     NUMBER NOT NULL,
    "IDESTADO"   NUMBER NOT NULL,
    constraint  "USUARIO_PK" primary key ("ID")
)
/

CREATE sequence "USUARIO_SEQ" 
/

CREATE trigger "BI_USUARIO"  
  before insert on "USUARIO"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "USUARIO_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/   

ALTER TABLE "USUARIO" ADD CONSTRAINT "USUARIO_FK" 
FOREIGN KEY ("IDTIPO")
REFERENCES "TIPOUSUARIO" ("ID")

/
ALTER TABLE "USUARIO" ADD CONSTRAINT "USUARIO_FK2" 
FOREIGN KEY ("IDESTADO")
REFERENCES "ESTADOUSUARIO" ("ID")
/

-------------------------------------------------------------------
CREATE table "CATEGORIA" (
    "ID"         NUMBER NOT NULL,
    "NOMBRE"     VARCHAR2(50) NOT NULL,
    constraint  "CATEGORIA_PK" primary key ("ID")
)
/

CREATE sequence "CATEGORIA_SEQ" 
/

CREATE trigger "BI_CATEGORIA"  
  before insert on "CATEGORIA"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "CATEGORIA_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/

-------------------------------------------------------------------
CREATE table "PRODUCTO" (
    "CODIGO"         NUMBER NOT NULL,
    "NOMBRE"    VARCHAR2(50) NOT NULL,
    "DESCRIPCION" VARCHAR2(255) NOT NULL,
    "IDCATEGORIA" NUMBER NOT NULL,
    constraint  "PRODUCTO_PK" primary key ("CODIGO")
)
/
 
ALTER TABLE "PRODUCTO" ADD CONSTRAINT "PRODUCTO_FK" 
FOREIGN KEY ("IDCATEGORIA")
REFERENCES "CATEGORIA" ("ID")
/
-------------------------------------------------------------------
CREATE table "CLIENTE" (
    "CEDULA"         NUMBER NOT NULL,
    "NOMBRE"    VARCHAR2(50) NOT NULL,
    "APELLIDO"    VARCHAR2(50) NOT NULL,
    "DIRECCION"    VARCHAR2(50) NOT NULL,
    "TELEFONO"    VARCHAR2(50) NOT NULL,
    constraint  "CLIENTE_PK" primary key ("CEDULA")
)
-------------------------------------------------------------------
CREATE table "INVENTARIO" (
    "ID"         NUMBER NOT NULL,
    "CODPRODUCTO"    NUMBER NOT NULL,
    "FECHA" DATE NOT NULL,
    "CANTIDAD"     NUMBER NOT NULL,
    "PRECIOIN"     NUMBER NOT NULL,
    "PRECIOOUT"   NUMBER NOT NULL,
    constraint  "INVENTARIO_PK" primary key ("ID")
)
/

CREATE sequence "INVENTARIO_SEQ" 
/

CREATE trigger "BI_INVENTARIO"  
  before insert on "INVENTARIO"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "INVENTARIO_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/   

ALTER TABLE "INVENTARIO" ADD CONSTRAINT "INVENTARIO_FK" 
FOREIGN KEY ("CODPRODUCTO")
REFERENCES "PRODUCTO" ("CODIGO")

/
-------------------------------------------------------------------
CREATE table "FACTURA" (
    "ID"         NUMBER NOT NULL,
    "IDUSUARIO"    NUMBER NOT NULL,
    "IDCLIENTE"     NUMBER NOT NULL,
    "FECHA" DATE NOT NULL,
    constraint  "FACTURA_PK" primary key ("ID")
)
/

CREATE sequence "FACTURA_SEQ" 
/

CREATE trigger "BI_FACTURA"  
  before insert on "FACTURA"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "FACTURA_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/   

ALTER TABLE "FACTURA" ADD CONSTRAINT "FACTURA_FK" 
FOREIGN KEY ("IDUSUARIO")
REFERENCES "USUARIO" ("ID")
/

ALTER TABLE "FACTURA" ADD CONSTRAINT "FACTURA_FK2" 
FOREIGN KEY ("IDCLIENTE")
REFERENCES "CLIENTE" ("CEDULA")

/
-------------------------------------------------------------------
CREATE table "DETFACTURA" (
    "ID"         NUMBER NOT NULL,
    "IDFACTURA"    NUMBER NOT NULL,
    "IDINVENTARIO"     NUMBER NOT NULL,
    "CANTIDAD" NUMBER NOT NULL,
    constraint  "DETFACTURA_PK" primary key ("ID")
)
/

CREATE sequence "DETFACTURA_SEQ" 
/

CREATE trigger "BI_DETFACTURA"  
  before insert on "DETFACTURA"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "DETFACTURA_SEQ".nextval into :NEW."ID" from dual;
  end if;
end;
/   

ALTER TABLE "DETFACTURA" ADD CONSTRAINT "DETFACTURA_FK" 
FOREIGN KEY ("IDFACTURA")
REFERENCES "FACTURA" ("ID")
/

ALTER TABLE "DETFACTURA" ADD CONSTRAINT "DETFACTURA_FK2" 
FOREIGN KEY ("IDINVENTARIO")
REFERENCES "INVENTARIO" ("ID")

/