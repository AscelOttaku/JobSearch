function addSearchField() {
    const container = document.getElementById('search-fields');

    const div = document.createElement('div');
    div.className = 'search-field flex items-center gap-2 mb-3 p-3 bg-gray-50 rounded-lg';

    const isOr = fields[0] && fields[0].startsWith("OR_");
    const logicSelect = document.createElement('select');
    logicSelect.name = 'logic';
    logicSelect.className = 'px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500';

    const andOption = document.createElement('option');
    andOption.value = 'AND';
    andOption.textContent = 'AND';
    if (!isOr) andOption.selected = true;

    const orOption = document.createElement('option');
    orOption.value = 'OR';
    orOption.textContent = 'OR';
    if (isOr) orOption.selected = true;

    logicSelect.append(andOption, orOption);

    const fieldSelect = document.createElement('select');
    fieldSelect.name = 'fieldName';
    fieldSelect.className = 'px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500';
    fieldSelect.onchange = function() { updateLogicSelect(this); };

    fields.forEach(field => {
        const option = document.createElement('option');
        option.value = field;
        option.textContent = field.startsWith("OR_")
            ? "ИЛИ " + capitalize(field.substring(3))
            : capitalize(field);
        fieldSelect.appendChild(option);
    });

    const operationSelect = document.createElement('select');
    operationSelect.name = 'operation';
    operationSelect.className = 'px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500';

    const operations = [
        { value: ':', text: '=' },
        { value: '>', text: '>' },
        { value: '<', text: '<' },
        { value: '*/', text: 'Starts with' },
        { value: '/*', text: 'Ends with' }
    ];

    operations.forEach(op => {
        const option = document.createElement('option');
        option.value = op.value;
        option.textContent = op.text;
        operationSelect.appendChild(option);
    });

    const valueInput = document.createElement('input');
    valueInput.type = 'text';
    valueInput.name = 'value';
    valueInput.placeholder = 'Enter value';
    valueInput.className = 'flex-1 px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500';

    const removeButton = document.createElement('button');
    removeButton.type = 'button';
    removeButton.textContent = 'Remove';
    removeButton.className = `
    px-3 py-2 
    text-blue-600 bg-blue-50 
    hover:bg-blue-100 
    focus:outline-none focus:ring-2 focus:ring-blue-200 
    rounded-md 
    transition-colors duration-200
`;
    removeButton.onclick = function() { removeField(this); };

    div.append(
        logicSelect,
        fieldSelect,
        operationSelect,
        valueInput,
        removeButton
    );

    container.appendChild(div);
}

function updateLogicSelect(fieldSelect) {
    const logicSelect = fieldSelect.parentNode.querySelector('select[name="logic"]');
    if (fieldSelect.value.startsWith("OR_")) {
        logicSelect.value = "OR";
    } else {
        logicSelect.value = "AND";
    }
}

function removeField(button) {
    button.parentNode.remove();
}

function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

function buildSearchCriteria() {
    const fields = document.querySelectorAll('.search-field');
    let criteria = '';

    fields.forEach(field => {
        const logic = field.querySelector('select[name="logic"]').value;
        let name = field.querySelector('select[name="fieldName"]').value;
        const op = field.querySelector('select[name="operation"]').value;
        let value = field.querySelector('input[name="value"]').value;
        if (name && op && value) {
            if (logic === "OR" && !name.startsWith("OR_")) {
                name = "OR_" + name;
            }

            if (op === "*/") {
                value = value + "*";
                criteria += `${name}:${value},`;
            } else if (op === "/*") {
                value = "*" + value;
                criteria += `${name}:${value},`;
            } else {
                criteria += `${name}${op}${value},`;
            }
        }
    });

    document.getElementById('searchCriteriaInput').value = criteria;
    return true;
}