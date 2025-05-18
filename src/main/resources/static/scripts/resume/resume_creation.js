const workExperiences = document.getElementById("workExperiences")
const educationInfos = document.getElementById("educationInfos")

const addEduCationButton = document.getElementById("addEduCationButton");
const addWorkExperienceButton = document.getElementById("addWorkExperienceButton");

let educationInfoCounter = 0;
let workExperienceCounter = 0;

const labels = {
    ru: {
        years: "Опыт (годы)",
        yearsPlaceholder: "Введите количество лет",
        companyName: "Компания",
        companyNamePlaceholder: "Введите название компании",
        position: "Должность",
        positionPlaceholder: "Введите должность",
        responsibilities: "Обязанности",
        responsibilitiesPlaceholder: "Опишите ваши обязанности"
    },
    en: {
        years: "Years of Work Experience",
        yearsPlaceholder: "Enter number of years",
        companyName: "Company",
        companyNamePlaceholder: "Enter company name",
        position: "Position",
        positionPlaceholder: "Enter position",
        responsibilities: "Responsibilities",
        responsibilitiesPlaceholder: "Describe your responsibilities"
    }
};

const educationLabels = {
    ru: {
        education: "Оброзование",
        degreePlaceholder: "Введите степень",
        institution: "Учебное заведение",
        institutionPlaceholder: "Введите название учебного заведения",
        program: "Программа",
        programPlaceholder: "Введите программу обучения",
        startDate: "Дата начала",
        endDate: "Дата окончания",
        datePlaceholder: "дд.мм.гггг HH:mm:ss"
    },
    en: {
        education: "Education",
        degreePlaceholder: "Enter degree",
        institution: "Institution",
        institutionPlaceholder: "Enter institution",
        program: "Program",
        programPlaceholder: "Enter program",
        startDate: "Start date",
        endDate: "End date",
        datePlaceholder: "yyyy-mm-dd HH:mm:ss",
    }
};

const currentLanguage = document.documentElement.lang || 'ru';

addWorkExperienceButton.addEventListener("click", () => {
    workExperienceCounter++;

    const langLabels = labels[currentLanguage] || labels['ru'];

    let newWorkExperience = document.createElement("div");
    newWorkExperience.classList.add("work-experience-block");
    newWorkExperience.innerHTML =
        `<div class="row mb-3">
            <div class="col-md-6">
                <div class="mb-3">
                    <label class="form-label">
                        <i class="bi bi-calendar-check form-icon me-2"></i>${langLabels.years}
                    </label>
                    <input type="number" 
                           name="workExperienceInfoDtos[${workExperienceCounter}].years"
                           class="form-control" 
                           placeholder="${langLabels.yearsPlaceholder}" 
                           min="0" />
                </div>

                <div class="mb-3">
                    <label class="form-label">
                        <i class="bi bi-building form-icon me-2"></i>${langLabels.companyName}
                    </label>
                    <input type="text" 
                           name="workExperienceInfoDtos[${workExperienceCounter}].companyName"
                           class="form-control" 
                           placeholder="${langLabels.companyNamePlaceholder}" />
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <label class="form-label">
                        <i class="bi bi-person-badge form-icon me-2"></i>${langLabels.position}
                    </label>
                    <input type="text" 
                           name="workExperienceInfoDtos[${workExperienceCounter}].position"
                           class="form-control" 
                           placeholder="${langLabels.positionPlaceholder}" />
                </div>

                <div class="mb-3">
                    <label class="form-label">
                        <i class="bi bi-list-task form-icon me-2"></i>${langLabels.responsibilities}
                    </label>
                    <textarea name="workExperienceInfoDtos[${workExperienceCounter}].responsibilities"
                              class="form-control"
                              placeholder="${langLabels.responsibilitiesPlaceholder}"
                              rows="3"></textarea>
                </div>
            </div>
        </div>`;


    const deleteButton = document.createElement("button")
    deleteButton.className = "btn btn-danger btn-sm d-flex align-items-center ms-auto";
    deleteButton.innerHTML = '<i class="bi bi-dash-lg me-1"></i>Удалить';
    deleteButton.addEventListener("click", () => {
        workExperienceCounter--;
        workExperiences.removeChild(newWorkExperience);
    })

    const buttonWrapper = wrapButton(deleteButton);

    newWorkExperience.appendChild(buttonWrapper);

    workExperiences.appendChild(newWorkExperience)
})

addEduCationButton.addEventListener("click", () => {
    educationInfoCounter++;

    const l = educationLabels[currentLanguage] || educationLabels['ru'];

    let newEducationInfo = document.createElement("div")
    newEducationInfo.className = "row mb-3";
    newEducationInfo.innerHTML = `
  <div class="col-md-6">
    <div class="mb-3">
      <label for="degree_${educationInfoCounter}" class="form-label">
        <i class="bi bi-award form-icon me-2"></i>${l.education}
      </label>
      <input type="text" name="educationInfoDtos[${educationInfoCounter}].degree"
             id="degree_${educationInfoCounter}"
             class="form-control"
             placeholder="${l.degreePlaceholder}"/>
    </div>

    <div class="mb-3">
      <label for="institution_${educationInfoCounter}" class="form-label">
        <i class="bi bi-journal-bookmark form-icon me-2"></i>${l.institution}
      </label>
      <input type="text" name="educationInfoDtos[${educationInfoCounter}].institution"
             id="institution_${educationInfoCounter}"
             class="form-control"
             placeholder="${l.institutionPlaceholder}"/>
    </div>

    <div class="mb-3">
      <label for="program_${educationInfoCounter}" class="form-label">
        <i class="bi bi-journal-bookmark form-icon me-2"></i>${l.program}
      </label>
      <input type="text" name="educationInfoDtos[${educationInfoCounter}].program"
             id="program_${educationInfoCounter}"
             class="form-control"
             placeholder="${l.programPlaceholder}"/>
    </div>
  </div>

  <div class="col-md-6">
    <div class="mb-3">
      <label for="startDate_${educationInfoCounter}" class="form-label">
        <i class="bi form-icon me-2"></i>${l.startDate}
      </label>
      <input type="date" name="educationInfoDtos[${educationInfoCounter}].startDate"
             id="startDate_${educationInfoCounter}"
             class="form-control"
             placeholder="${l.datePlaceholder}"/>
    </div>

    <div class="mb-3">
      <label for="endDate_${educationInfoCounter}" class="form-label">
        <i class="bi form-icon me-2"></i>${l.endDate}
      </label>
      <input type="date" name="educationInfoDtos[${educationInfoCounter}].endDate"
             id="endDate_${educationInfoCounter}"
             class="form-control"
             placeholder="${l.datePlaceholder}"/>
    </div>
  </div>
`;


    const deleteButton = document.createElement("button")
    deleteButton.className = "btn btn-danger btn-sm d-flex align-items-center ms-auto";
    deleteButton.innerHTML = '<i class="bi bi-dash-lg me-1"></i>Удалить';
    deleteButton.addEventListener("click", () => {
        educationInfoCounter--;
        educationInfos.removeChild(newEducationInfo)
    })

    const buttonWrapper = wrapButton(deleteButton);
    newEducationInfo.appendChild(buttonWrapper);

    educationInfos.appendChild(newEducationInfo);
})

function wrapButton(button) {
    const buttonWrapper = document.createElement("div");
    buttonWrapper.className = "d-flex justify-content-end mb-2";
    buttonWrapper.appendChild(button);
    return buttonWrapper;
}