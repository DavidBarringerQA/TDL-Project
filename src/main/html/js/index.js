(() => {
		let list = document.querySelector("#list");
		let description = document.querySelector("#description");
		let priority = document.querySelector("#priority");
		let time = document.querySelector("#time");
		time.min = new Date().toISOString().split(".")[0];
		let submitButton = document.querySelector("#submit");
		let form = document.querySelector("#newItem");
		let addButton = document.querySelector("#addButton");
		let nextId = 1;

		getData();

		function createWithContent(type, content){
				let element = document.createElement(type);
				element.textContent = content;
				return element;
		}

		function prioToString(priority){
				switch(priority){
				case 1:
				case "1": return "Highest";
				case 2:
				case "2": return "High";
				case 3:
				case "3": return "Medium";
				case 4:
				case "4": return "Low";
				case 5:
				case "5": return "Lowest";
				default: console.error(`${priority} is not a valid value for priority`);
						return "ERROR";
				}
		}

		function makePrioElement(){
				let prioElement = document.createElement("select");
				for(let i = 1; i <= 5; i++){
						let optionElement = createWithContent("option", prioToString(i));
						optionElement.value = i;
						prioElement.appendChild(optionElement);
				}
				return prioElement;
		}

		function editData(tr, rowId){
				return {
						id: rowId,
						description: tr.querySelector(`#edited-description-${rowId}`).value,
						priority: tr.querySelector(`#edited-priority-${rowId}`).value,
						time: new Date(tr.querySelector(`#edited-time-${rowId}`).value).getTime(),
						completed: tr.querySelector(`#edited-completed-${rowId}`).value
				}
		}

		function editRow(tr, rowObj){
				let children = tr.children;
				for(let i = children.length - 1; i >= 1; i--){
						if(i < 5){
								let newChild = document.createElement("td");
								let newValue;
								switch(i){
								case 1: 
										newValue = document.createElement("input");
										newValue.value = rowObj.description;
										newValue.id = `edited-description-${rowObj.id}`;
										break;
								case 2:
										newValue = makePrioElement();
										newValue.value = rowObj.priority;
										newValue.id = `edited-priority-${rowObj.id}`;
										break;
								case 3:
										newValue = document.createElement("input");
										newValue.value = new Date(rowObj.time).toISOString().split(".")[0];
										newValue.type = "datetime-local";
										newValue.id = `edited-time-${rowObj.id}`;
										break;
								case 4:
										newValue = document.createElement("select");
										let optFalse = createWithContent("option", "false");
										let optTrue = createWithContent("option", "true");
										newValue.appendChild(optFalse);
										newValue.appendChild(optTrue);
										newValue.id = `edited-completed-${rowObj.id}`;
										newValue.value = rowObj.completed;
										break;
								}
								newChild.appendChild(newValue);
								tr.replaceChild(newChild, children[i]);
						}
						else {
								tr.removeChild(children[i]);
						}
				}
				let confirmRow = document.createElement("td");
				let confirmButton = createWithContent("button", "confirm");
				confirmButton.addEventListener("click", (event) => {
						const data = editData(tr, rowObj.id);
						console.log(data);
						list.replaceChild(makeElement(data), tr);
						fetch(`http://localhost:8000/${rowObj.id}`, {
								method: "PUT",
								headers: {
										"Content-type": "application/json;"
								},
								body: JSON.stringify(data)
						}).then(response => {
								if(response.status !== 200){
										console.error(response.status);
								}
								else{
										return response.json();
								}
						}).catch(err => list.replaceChild(tr, makeElement(data)))
								.then(returnedData => {
								console.log(returnedData);	
						}).catch(err => console.log(err));
				});
				confirmRow.appendChild(confirmButton);
				tr.appendChild(confirmRow);
				return tr;
		}

		function makeElement(row){
				let tr = document.createElement("tr");
				let rowId = createWithContent("td", row.id);
				let rowDesc = createWithContent("td", row.description);
				let rowPrio = createWithContent("td", prioToString(row.priority));
				let rowTime = createWithContent("td", new Date(row.time).toUTCString());
				let rowCompleted = createWithContent("td", row.completed);
				let rowButtons = document.createElement("td");
				let editButton = createWithContent("button", "edit");
				let deleteButton = createWithContent("button", "delete");
				deleteButton.addEventListener ("click", (event) => {
						tr.remove();
						fetch(`http://localhost:8000/${row.id}`, {
								method: "DELETE"
						}).then(response => {
								if(response.status !== 200){
										console.error(response.status);
								}
								else {
										return response.json();
								}
						}).then(data => console.log(data))
								.catch(err => console.error(err));
				});
				editButton.addEventListener("click", (event) => {
						editRow(tr, row);
				});
				rowButtons.appendChild(editButton);
				rowButtons.appendChild(deleteButton);
				tr.appendChild(rowId);
				tr.appendChild(rowDesc);
				tr.appendChild(rowPrio);
				tr.appendChild(rowTime);
				tr.appendChild(rowCompleted);
				tr.appendChild(rowButtons);
				return tr;
		}

		submitButton.addEventListener("click", (event) => {
				event.preventDefault();
				const body = createData();
				let demoData = body;
				demoData.id = nextId++;
				let element = makeElement(demoData);
				list.appendChild(element);
				fetch("http://localhost:8000/create", {
						method: "POST",
						headers: {
								"Content-type": "application/json"
						},
						body: JSON.stringify(body)
				})
						.then(response => {
								if(response.status !== 201){
										console.error(response.status);
								}
								else{
										return response.json();
								}
						}).catch(err => list.removeChild(element))
						.then(data => {
								console.log(data);	
						}).catch(err => {
								console.error(err);
						});
				console.log(JSON.stringify(demoData));
		});

		function createData(){
				return {
						description: description.value,
						priority: priority.value,
						time: new Date(time.value).getTime(),
						completed: false
				};
		}

		function getData(){
				fetch("http://localhost:8000/")
						.then(response => {
								console.log(response);
								if(response.status !== 200){
										console.error(response.status);
								}
								else{
										return response.json();
								}
						}).then(data => {
								console.log(data);
								for(dataRow of data){
										list.appendChild(makeElement(dataRow));
										nextId = dataRow.id + 1;
								}
						}).catch(err => console.error(err));
		}

		addButton.addEventListener("click", (event) => {
				form.classList.toggle("hidden");
		});

})();
