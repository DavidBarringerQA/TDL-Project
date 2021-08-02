(() => {
		let list = document.querySelector("#list");
		let description = document.querySelector("#description");
		let priority = document.querySelector("#priority");
		let time = document.querySelector("#time");
		time.min = new Date().toISOString().split(".")[0];
		let submitButton = document.querySelector("#submit");
		let nextId = 2;

		let dummy = {
				id: 1,
				description: "Test item",
				priority: 2,
				time: new Date().getTime(),
				completed: false
		}
		list.appendChild(makeElement(dummy));

		function createWithContent(type, content){
				let element = document.createElement(type);
				element.textContent = content;
				return element;
		}

		function prioToString(priority){
				switch(priority){
				case 1:
				case "1": return "Highest";
						break;
				case 2:
				case "2": return "High";
						break;
				case 3:
				case "3": return "Medium";
						break;
				case 4:
				case "4": return "Low";
						break;
				case 5:
				case "5": return "Lowest";
						break;
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
						fetch(`http://localhost:8080/${rowObj.id}`, {
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
										response.json();
								}
						}).then(response => {
								console.log(response);	
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
				let rowTime = createWithContent("td", new Date(row.time).toDateString());
				let rowCompleted = createWithContent("td", row.completed);
				let rowEdit = document.createElement("td");
				let editButton = createWithContent("button", "edit");
				let rowDelete = document.createElement("td");
				let deleteButton = createWithContent("button", "delete");
				deleteButton.addEventListener ("click", (event) => {
						tr.remove();
						fetch(`http://localhost:8080/delete/${row.id}`, {
								method: "DELETE"
						}).then(response => {
								if(response.status !== 200){
										console.error(response.status);
								}
								else {
										response.json();
								}
						}).then(data => console.log(data))
								.catch(err => console.error(err));
				});
				rowDelete.appendChild(deleteButton);
				editButton.addEventListener("click", (event) => {
						editRow(tr, row);
				});
				rowEdit.appendChild(editButton);
				tr.appendChild(rowId);
				tr.appendChild(rowDesc);
				tr.appendChild(rowPrio);
				tr.appendChild(rowTime);
				tr.appendChild(rowCompleted);
				tr.appendChild(rowEdit);
				tr.appendChild(rowDelete);
				return tr;
		}

		submitButton.addEventListener("click", (event) => {
				event.preventDefault();
				const body = createData();
				let demoData = body;
				demoData.id = nextId++;
				list.appendChild(makeElement(demoData));
				fetch("http://localhost:8080/create", {
						method: "POST",
						headers: {
								"Content-type": "application/json;"
						},
						body: (JSON.stringify(body))
				}).then(response => {
						if(response.status !== 201){
								console.error(response.status);
						}
						else{
								response.json();
						}
				}).then(data => {
						console.log(data);	
				}).catch(err => console.error(err));
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
				fetch("http://localhost:8080/")
						.then(respone => {
								if(response.status !== 200){
										console.error(response.status);
								}
								else{
										response.json();
								}
						}).then(data => {
								for(dataRow of data){
										list.appendChild(makeElement(dataRow));
								}
						});
		}

})();
