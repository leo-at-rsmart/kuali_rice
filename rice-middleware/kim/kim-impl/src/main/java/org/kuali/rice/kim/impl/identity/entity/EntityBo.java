/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kim.impl.identity.entity;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.kim.api.identity.EntityUtils;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliation;
import org.kuali.rice.kim.api.identity.citizenship.EntityCitizenship;
import org.kuali.rice.kim.api.identity.employment.EntityEmployment;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityContract;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifier;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.kim.api.identity.personal.EntityEthnicity;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.identity.residency.EntityResidency;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.kim.api.identity.visa.EntityVisa;
import org.kuali.rice.kim.impl.identity.affiliation.EntityAffiliationBo;
import org.kuali.rice.kim.impl.identity.citizenship.EntityCitizenshipBo;
import org.kuali.rice.kim.impl.identity.employment.EntityEmploymentBo;
import org.kuali.rice.kim.impl.identity.external.EntityExternalIdentifierBo;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.kuali.rice.kim.impl.identity.personal.EntityBioDemographicsBo;
import org.kuali.rice.kim.impl.identity.personal.EntityEthnicityBo;
import org.kuali.rice.kim.impl.identity.principal.PrincipalBo;
import org.kuali.rice.kim.impl.identity.privacy.EntityPrivacyPreferencesBo;
import org.kuali.rice.kim.impl.identity.residency.EntityResidencyBo;
import org.kuali.rice.kim.impl.identity.type.EntityTypeContactInfoBo;
import org.kuali.rice.kim.impl.identity.visa.EntityVisaBo;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "KRIM_ENTITY_T")
public class EntityBo extends PersistableBusinessObjectBase implements EntityContract {
    @Id
    @Column(name = "ENTITY_ID")
    private String id;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityNameBo> names = new ArrayList<EntityNameBo>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private List<PrincipalBo> principals = new ArrayList<PrincipalBo>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityExternalIdentifierBo> externalIdentifiers = new ArrayList<EntityExternalIdentifierBo>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityAffiliationBo> affiliations = new ArrayList<EntityAffiliationBo>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityEmploymentBo> employmentInformation = new ArrayList<EntityEmploymentBo>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityTypeContactInfoBo> entityTypeContactInfos = new ArrayList<EntityTypeContactInfoBo>();
    @OneToOne(targetEntity = EntityPrivacyPreferencesBo.class, fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "ENTITY_ID", insertable = false,
            updatable = false)
    private EntityPrivacyPreferencesBo privacyPreferences;
    @OneToOne(targetEntity = EntityBioDemographicsBo.class, fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(
            name = "ENTITY_ID", insertable = false, updatable = false)
    private EntityBioDemographicsBo bioDemographics;
    @OneToMany(targetEntity = EntityCitizenshipBo.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityCitizenshipBo> citizenships = new ArrayList<EntityCitizenshipBo>();
    @OneToMany(targetEntity = EntityEthnicityBo.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityEthnicityBo> ethnicities = new ArrayList<EntityEthnicityBo>();
    @OneToMany(targetEntity = EntityResidencyBo.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityResidencyBo> residencies = new ArrayList<EntityResidencyBo>();
    @OneToMany(targetEntity = EntityVisaBo.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ENTITY_ID", insertable = false, updatable = false)
    private List<EntityVisaBo> visas = new ArrayList<EntityVisaBo>();
    @Column(name = "ACTV_IND")
    private boolean active;

    public static org.kuali.rice.kim.api.identity.entity.Entity to(EntityBo bo) {
        if (bo == null) {
            return null;
        }

        return Entity.Builder.create(bo).build();
    }

    public static EntityDefault toDefault(EntityBo bo) {
        if (bo == null) {
            return null;
        }

        return EntityDefault.Builder.create(bo).build();
    }

    public static EntityBo from(org.kuali.rice.kim.api.identity.entity.Entity immutable) {
        return fromAndUpdate(immutable, null);
    }

    /**
     * Creates a EntityBo business object from an immutable representation of a Entity.
     *
     * @param immutable an immutable Entity
     * @return a EntityBo
     */
    public static EntityBo fromAndUpdate(Entity immutable, EntityBo toUpdate) {
        if (immutable == null) {
            return null;
        }

        EntityBo bo = toUpdate;
        if (toUpdate == null) {
            bo = new EntityBo();
        }

        bo.active = immutable.isActive();
        bo.id = immutable.getId();

        bo.names = new ArrayList<EntityNameBo>();
        if (CollectionUtils.isNotEmpty(immutable.getNames())) {
            for (EntityName name : immutable.getNames()) {
                bo.names.add(EntityNameBo.from(name));
            }

        }

        bo.principals = new ArrayList<PrincipalBo>();
        if (CollectionUtils.isNotEmpty(immutable.getPrincipals())) {
            for (Principal principal : immutable.getPrincipals()) {
                bo.principals.add(PrincipalBo.from(principal));
            }

        }

        bo.externalIdentifiers = new ArrayList<EntityExternalIdentifierBo>();
        if (CollectionUtils.isNotEmpty(immutable.getExternalIdentifiers())) {
            for (EntityExternalIdentifier externalId : immutable.getExternalIdentifiers()) {
                bo.externalIdentifiers.add(EntityExternalIdentifierBo.from(externalId));
            }

        }

        bo.affiliations = new ArrayList<EntityAffiliationBo>();
        if (CollectionUtils.isNotEmpty(immutable.getAffiliations())) {
            for (EntityAffiliation affiliation : immutable.getAffiliations()) {
                bo.affiliations.add(EntityAffiliationBo.from(affiliation));
            }

        }

        bo.employmentInformation = new ArrayList<EntityEmploymentBo>();
        if (CollectionUtils.isNotEmpty(immutable.getEmploymentInformation())) {
            for (EntityEmployment employment : immutable.getEmploymentInformation()) {
                bo.employmentInformation.add(EntityEmploymentBo.from(employment));
            }

        }

        bo.entityTypeContactInfos = new ArrayList<EntityTypeContactInfoBo>();
        if (CollectionUtils.isNotEmpty(immutable.getEntityTypeContactInfos())) {
            for (EntityTypeContactInfo entityType : immutable.getEntityTypeContactInfos()) {
                bo.entityTypeContactInfos.add(EntityTypeContactInfoBo.from(entityType));
            }

        }

        if (immutable.getPrivacyPreferences() != null) {
            bo.privacyPreferences = EntityPrivacyPreferencesBo.from(immutable.getPrivacyPreferences());
        }

        if (immutable.getBioDemographics() != null) {
            bo.bioDemographics = EntityBioDemographicsBo.from(immutable.getBioDemographics());
        }

        bo.citizenships = new ArrayList<EntityCitizenshipBo>();
        if (CollectionUtils.isNotEmpty(immutable.getCitizenships())) {
            for (EntityCitizenship citizenship : immutable.getCitizenships()) {
                bo.citizenships.add(EntityCitizenshipBo.from(citizenship));
            }

        }

        bo.ethnicities = new ArrayList<EntityEthnicityBo>();
        if (CollectionUtils.isNotEmpty(immutable.getEthnicities())) {
            for (EntityEthnicity ethnicity : immutable.getEthnicities()) {
                bo.ethnicities.add(EntityEthnicityBo.from(ethnicity));
            }

        }

        bo.residencies = new ArrayList<EntityResidencyBo>();
        if (CollectionUtils.isNotEmpty(immutable.getResidencies())) {
            for (EntityResidency residency : immutable.getResidencies()) {
                bo.residencies.add(EntityResidencyBo.from(residency));
            }

        }

        bo.visas = new ArrayList<EntityVisaBo>();
        if (CollectionUtils.isNotEmpty(immutable.getVisas())) {
            for (EntityVisa visa : immutable.getVisas()) {
                bo.visas.add(EntityVisaBo.from(visa));
            }

        }

        bo.setVersionNumber(immutable.getVersionNumber());
        bo.setObjectId(immutable.getObjectId());

        return bo;
    }

    @Override
    public EntityTypeContactInfoBo getEntityTypeContactInfoByTypeCode(String entityTypeCode) {
        if (CollectionUtils.isEmpty(this.entityTypeContactInfos)) {
            return null;
        }

        for (EntityTypeContactInfoBo entType : this.entityTypeContactInfos) {
            if (entType.getEntityTypeCode().equals(entityTypeCode)) {
                return entType;
            }

        }

        return null;
    }

    @Override
    public EntityEmploymentBo getPrimaryEmployment() {
        if (CollectionUtils.isEmpty(this.employmentInformation)) {
            return null;
        }

        for (EntityEmploymentBo employment : this.employmentInformation) {
            if (employment.isPrimary() && employment.isActive()) {
                return employment;
            }

        }

        return null;
    }

    @Override
    public EntityAffiliationBo getDefaultAffiliation() {
        return EntityUtils.getDefaultItem(this.affiliations);
    }

    @Override
    public EntityExternalIdentifierBo getEntityExternalIdentifier(String externalIdentifierTypeCode) {
        if (CollectionUtils.isEmpty(this.externalIdentifiers)) {
            return null;
        }

        for (EntityExternalIdentifierBo externalId : this.externalIdentifiers) {
            if (externalId.getExternalIdentifierTypeCode().equals(externalIdentifierTypeCode)) {
                return externalId;
            }

        }

        return null;
    }

    @Override
    public EntityNameContract getDefaultName() {
        return EntityUtils.getDefaultItem(this.names);
    }

    @Override
    public EntityPrivacyPreferencesBo getPrivacyPreferences() {
        return this.privacyPreferences;
    }

    @Override
    public EntityBioDemographicsBo getBioDemographics() {
        return this.bioDemographics;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<EntityNameBo> getNames() {
        return names;
    }

    public void setNames(List<EntityNameBo> names) {
        this.names = names;
    }

    @Override
    public List<PrincipalBo> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<PrincipalBo> principals) {
        this.principals = principals;
    }

    @Override
    public List<EntityExternalIdentifierBo> getExternalIdentifiers() {
        return externalIdentifiers;
    }

    public void setExternalIdentifiers(List<EntityExternalIdentifierBo> externalIdentifiers) {
        this.externalIdentifiers = externalIdentifiers;
    }

    @Override
    public List<EntityAffiliationBo> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<EntityAffiliationBo> affiliations) {
        this.affiliations = affiliations;
    }

    @Override
    public List<EntityEmploymentBo> getEmploymentInformation() {
        return employmentInformation;
    }

    public void setEmploymentInformation(List<EntityEmploymentBo> employmentInformation) {
        this.employmentInformation = employmentInformation;
    }

    @Override
    public List<EntityTypeContactInfoBo> getEntityTypeContactInfos() {
        return entityTypeContactInfos;
    }

    public void setEntityTypeContactInfos(List<EntityTypeContactInfoBo> entityTypeContactInfos) {
        this.entityTypeContactInfos = entityTypeContactInfos;
    }

    public void setPrivacyPreferences(EntityPrivacyPreferencesBo privacyPreferences) {
        this.privacyPreferences = privacyPreferences;
    }

    public void setBioDemographics(EntityBioDemographicsBo bioDemographics) {
        this.bioDemographics = bioDemographics;
    }

    @Override
    public List<EntityCitizenshipBo> getCitizenships() {
        return citizenships;
    }

    public void setCitizenships(List<EntityCitizenshipBo> citizenships) {
        this.citizenships = citizenships;
    }

    @Override
    public List<EntityEthnicityBo> getEthnicities() {
        return ethnicities;
    }

    public void setEthnicities(List<EntityEthnicityBo> ethnicities) {
        this.ethnicities = ethnicities;
    }

    @Override
    public List<EntityResidencyBo> getResidencies() {
        return residencies;
    }

    public void setResidencies(List<EntityResidencyBo> residencies) {
        this.residencies = residencies;
    }

    @Override
    public List<EntityVisaBo> getVisas() {
        return visas;
    }

    public void setVisas(List<EntityVisaBo> visas) {
        this.visas = visas;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
