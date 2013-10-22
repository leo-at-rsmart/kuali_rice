--
-- Copyright 2005-2013 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License")/
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--


CREATE TABLE TRVL_EXP_ITM_T  (
    EXP_ITM_ID    VARCHAR(40) NOT NULL,
    OBJ_ID        VARCHAR(36) NOT NULL,
   VER_NBR      DECIMAL(8,0) NOT NULL DEFAULT '1',
    EXP_TYP_CD    VARCHAR(40) NOT NULL,
    EXP_AMT       DECIMAL(19,2) NOT NULL,
    EXP_DESC      VARCHAR(255) NOT NULL,
    EXP_DT        DATETIME NULL,
    EXP_REIMB     VARCHAR(1) NULL DEFAULT 'Y',
    EXP_TXBL      VARCHAR(1) NULL DEFAULT 'Y',
    CONSTRAINT TRVL_EXP_ITM_TP1 PRIMARY KEY(EXP_ITM_ID),
    UNIQUE INDEX TRVL_EXP_ITEM_TC0 (OBJ_ID)
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/

CREATE TABLE TRVL_EXP_ITM_ID_S (
  id bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
/

INSERT INTO TRVL_EXP_ITM_ID_S VALUES (0)
/

INSERT INTO TRVL_EXP_ITM_T (EXP_ITM_ID, OBJ_ID,VER_NBR,EXP_TYP_CD,EXP_AMT,EXP_DESC, EXP_DT, EXP_REIMB, EXP_TXBL)
VALUES (last_insert_id(), uuid(), 1,'ME', 30, 'Family Related', NOW(), 'Y','Y')
/



CREATE TABLE TRVL_MLG_RT_T  (
    MLG_RT_ID     VARCHAR(40) NOT NULL,
    MLG_RT_CD     VARCHAR(40) NOT NULL,
    OBJ_ID        VARCHAR(36) NOT NULL,
   VER_NBR      DECIMAL(8,0) NOT NULL DEFAULT '1',
    MLG_RT_NM     VARCHAR(40) NOT NULL,
    MLG_RT      DECIMAL(8,0) NOT NULL,
    ACTV_IND      VARCHAR(1) NULL DEFAULT 'Y',
    CONSTRAINT TRVL_MLG_RT_TP1 PRIMARY KEY(MLG_RT_ID),
    UNIQUE INDEX TRVL_MIL_RT_TC0 (OBJ_ID)
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


CREATE TABLE TRVL_MLG_RT_ID_S (
  id bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
/
INSERT INTO TRVL_MLG_RT_ID_S VALUES (0)
/

INSERT INTO TRVL_MLG_RT_T (MLG_RT_ID, MLG_RT_CD,OBJ_ID,VER_NBR,MLG_RT_NM,MLG_RT, ACTV_IND)
VALUES (last_insert_id(), 'DO', uuid(), 1,'Domestic', 30, 'Y')
/

CREATE TABLE TRVL_DEST_T (
    TRVL_DEST_ID  VARCHAR(40) NOT NULL,
    TRVL_TYP_CD   VARCHAR(40) NOT NULL,
    OBJ_ID        VARCHAR(36) NOT NULL,
    VER_NBR      DECIMAL(8,0) NOT NULL DEFAULT '1',
    DEST_NM       VARCHAR(40) NOT NULL,
    POSTAL_CNTRY_CD VARCHAR(40) NOT NULL,
    POSTAL_STATE_CD  VARCHAR(40) NOT NULL,
    ACTV_IND      VARCHAR(1) NOT NULL DEFAULT 'Y',
    CONSTRAINT TRVL_DEST_TP1 PRIMARY KEY(TRVL_DEST_ID),
    UNIQUE INDEX TRVL_DEST_TC0 (OBJ_ID)
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/

CREATE TABLE TRVL_DEST_ID_S (
  id bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
/

INSERT INTO TRVL_DEST_ID_S values (0)
/

INSERT INTO TRVL_DEST_T (TRVL_DEST_ID, TRVL_TYP_CD, OBJ_ID, VER_NBR, DEST_NM, POSTAL_CNTRY_CD, POSTAL_STATE_CD, ACTV_IND)
VALUES (last_insert_id(), 'IS', uuid(), 1, 'Colorado', 'US', 'CO', 'Y')
/


CREATE TABLE TRVL_PD_EXP_T  (
    PD_EXP_ID     VARCHAR(40) NOT NULL,
    OBJ_ID      	VARCHAR(36) NOT NULL,
    VER_NBR     	DECIMAL(8,0) NOT NULL DEFAULT '1',
    PD_DT         DATETIME NULL,
    BKFST_VAL     DECIMAL(19,2)  NOT NULL,
    LNCH_VAL      DECIMAL(19,2)  NOT NULL,
    DNNR_VAL    DECIMAL(19,2)  NOT NULL,
    INCD_VAL      DECIMAL(19,2)  NOT NULL,
    MLG_RT_ID       VARCHAR(40) NOT NULL,
    MLG_EST       DECIMAL(8,0) NOT NULL,
	CONSTRAINT TRVL_PD_EXP_TP1 PRIMARY KEY(PD_EXP_ID),
   UNIQUE INDEX TRVL_PD_EXP_TC0 (OBJ_ID)
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/

CREATE TABLE TRVL_PD_EXP_ID_S (
  id bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
/

INSERT INTO TRVL_PD_EXP_ID_S values ( 0 )
/

INSERT INTO TRVL_PD_EXP_T values(last_insert_id(), uuid(), 1, NOW(), 10.00, 10.00, 15.00, 20.00, 10000, 30)
/


CREATE TABLE TRVL_CO_T (
    CO_ID         VARCHAR(40) NOT NULL,
    CO_NM         VARCHAR(40) NOT NULL,
    OBJ_ID        VARCHAR(36) NOT NULL,
    VER_NBR       DECIMAL(8,0) NOT NULL DEFAULT '1',
    ACTV_IND      VARCHAR(1) NOT NULL DEFAULT 'Y',
    CONSTRAINT TRVL_CO_TP1 PRIMARY KEY(CO_ID),
    UNIQUE INDEX TRVL_CO_TC0 (OBJ_ID)
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/

CREATE TABLE TRVL_CO_ID_S (
  id bigint(19) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
/

INSERT INTO TRVL_CO_ID_S VALUES ('0')
/

INSERT INTO TRVL_CO_T (CO_ID, CO_NM,OBJ_ID,VER_NBR, ACTV_IND ) VALUES (last_insert_id(), 'Value Rentals', uuid(),1,'Y')
/