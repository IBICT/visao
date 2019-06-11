// Iniciar mapa objeto sem zoom, latitude, longitude, zoom inicial
var map = L.map('map', {
        zoomControl:false,
        minZoom: 5,
        doubleClickZoom: false
    }).setView([-15, -50], 4);
// map.setMaxBounds(map.getBounds());

var cookies = document.cookie.split(';');
var existeCookie = false;
cookies.forEach(function (cookie) {
    if(cookie === " logged=true"){
        $('.show-logado').show();
        $('.show-deslogado').hide();
        existeCookie = true;
    }
});
if(!existeCookie) {
    $('.show-logado').hide();
    $('.show-deslogado').show();
}

var baseLayers = {
    'Gao De Image': L.layerGroup([L.tileLayer('http://webst0{s}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}', {
        subdomains: "1234"
    }), L.tileLayer('http://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}.png', {
        subdomains: "1234"
    })]),
    "Mapa tridimensional": L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
	    maxZoom: 18,
	    id: 'mapbox.satellite'
    }),
	'GeoQ': L.tileLayer('http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}'),
    "Mapa escuro": L.layerGroup([L.tileLayer('http://{s}.basemaps.cartocdn.com/dark_nolabels/{z}/{x}/{y}.png', {
        subdomains: "1234"
    }), L.tileLayer('http://{s}.basemaps.cartocdn.com/dark_only_labels/{z}/{x}/{y}.png', {
        subdomains: "1234"
    })]),
	"Mapa claro": L.layerGroup([L.tileLayer('http://{s}.basemaps.cartocdn.com/light_nolabels/{z}/{x}/{y}.png', {
        subdomains: "1234"
    }), L.tileLayer('http://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}.png', {
        subdomains: "1234"
    })])
    .addTo(map)
};

var layercontrol = L.control.layers(baseLayers,{}, {
    position: "topleft"
}).addTo(map);

markers = L.markerClusterGroup({ chunkedLoading: true });

var group = new L.FeatureGroup();

var arrayCompleto, municipioArray, estadoArray;
var indicatorArray, filterArray, years;
var estado, mesoRegiao, municipio;
var maxValue, minValue, midValue, categories, indicatorNames, groupLayer;
var indicadorMapControl, filtroMapControl, limparMapControl, rangeBarControl, mapControl, botao2, botao3;
var filtrosId, layersId;
var indicadorSelecionado;
var filtrosPesquisados = new Map();
var indicadorAtual;

var currentGroupCategory;
isUserAllowedGroupCategory();
$(".leaflet-right.leaflet-bottom").css('right', '340px');

function modalDetail(data){
	$('#modalEstadoSigla').html(data);
	$('#modalHabitantesValor').html("3.873.743");
	$('#exampleModal').modal('show');
}


function isUserAllowedGroupCategory(){
    var grpCatId = getUrlParameter('grpCat');

    if(grpCatId === undefined || grpCatId === 'null' || grpCatId === ''){
        window.location.replace("/#/accessdenied");
    }
    currentGroupCategory = grpCatId;

    $.ajax({
        url: '/api/group-categories-enabled/'+grpCatId,
        dataType: 'json',
        success: function (data) {
            if(!data){
                window.location.replace("/#/accessdenied");
            }
            getGeoJson();
        }
    });
}

function getGeoJson(){
	//usage in request
	$.ajax({
		url: 'estado.json',
		dataType: 'json',
		success: function(data) {
			estado = data;
			$.ajax({
				url: './municipio.json',
				dataType: 'json',
				success: function(data) {
					municipio = data;
					$.ajax({
						url: '/api/categoriesByGroupCategory/'+currentGroupCategory,
						dataType: 'json',
						success: function(data) {
							categories = data;
                            var categoriesIds = categories.map(function(element){return element.id}).join(',');
							$.ajax({
								url: '/api/namesPublic?&categoryId.in='+categoriesIds,
								dataType: 'json',
								success: function(data) {
									indicatorNames = data;
                                    $.ajax({
                                        url: '/api/filters?&categoryId.in='+categoriesIds,
                                        dataType: 'json',
                                        success: function (data) {
                                            filterArray = data;
                                            $.ajax({
                                                url: '/api/group-layers?&categoryId.in='+categoriesIds,
                                                dataType: 'json',
                                                success: function (data) {
                                                    groupLayer = data;
                                                    makeMap();
                                                }
                                            });
                                        }
                                    });
								}
							});
						}
					});
				}
			});
		}
	});
}

function genereteListIndicators(){
	var indicatorCategory = '';
	categories.filter(function (categoria) {
        return categoria.type === 'INDICATOR';
    }).forEach(function(item) {

		indicatorCategory += 	'					<div style="border-bottom: 1px solid #E0E0E0;" class="card">' +
								'						<div style="border: 0;" class="card-header" id="headingCard'+ item.id +'">' +
								'							<h5 class="mb-0">' +
								'								<button style="background-color: #fff;" class="btn btn-link" data-toggle="collapse" data-target="#collapse'+ item.id  +'" aria-expanded="true" aria-controls="collapseOne">' +
								'									<div class="robotoFamily" style="display: inline-block; text-overflow: ellipsis; overflow: hidden; width: 200px; height: 18px; white-space: nowrap;" title="'+ item.name +'">'+ item.name +' </div><i class="pull-right fa fa-angle-right" aria-hidden="true"></i>' +
								'								</button>' +
								'							</h5>' +
								'						</div>' +
								'						<div id="collapse'+ item.id  +'" class="collapse" aria-labelledby="headingCard'+ item.id +'" data-parent="#accordion">' +
								'							<div class="card-body">';

		indicatorNames.filter(function(indicator) {
			return indicator.category.name == item.name;
		}).forEach(function(indicator) {
            indicatorCategory += 	'								<div class="form-check">' +
                                    '                                   <div class="custom-control custom-radio">' +
                                    '                                       <input type="radio" id="indicador'+ indicator.id +'" name="indicator" class="custom-control-input form-check-input" value="'+indicator.id+'">' +
                                    '                                       <label class="custom-control-label form-check-label robotoFamily" for="indicador'+ indicator.id +'">'+ indicator.value +'</label>' +
                                    '                                   </div>' +
									'								</div>';
		});

		indicatorCategory +=	'							</div>' +
								'						</div>' +
								'					</div>';
	});
	return indicatorCategory;
}

function generateListFilters(){
    var filterCategory = '';

    categories.filter(function (categoria) {
        return categoria.type === 'FILTER';
    }).forEach(function(item) {
        filterCategory +=
            '<div style="border-bottom: 1px solid #E0E0E0;" class="card">' +
            '   <div style="border: 0;" class="card-header" id="headingCard'+ item.id +'">' +
            '	    <h5 class="mb-0">' +
            '		    <button style="background-color: #fff;" class="btn btn-link" data-toggle="collapse" data-target="#collapse'+ item.id  +'" aria-expanded="true" aria-controls="collapseOne">' +
            '			    <div class="robotoFamily" style="display: inline-block; text-overflow: ellipsis; overflow: hidden; width: 200px; height: 18px; white-space: nowrap;" title="'+ item.name +'">'+ item.name +' </div><i class="pull-right fa fa-angle-right" aria-hidden="true"></i>' +
            '			</button>' +
            '		</h5>' +
            '	</div>' +
            '   <div id="collapse'+ item.id  +'" class="collapse" aria-labelledby="headingCard'+ item.id +'" data-parent="#accordionFilter">' +
            '	    <div class="card-body">';

        filterArray.filter(function(filter) {
            return filter.category.id == item.id;
        }).forEach(function(item) {
            filterCategory +=
                '       <div class="form-check-input" style="display: contents">'+
                '           <div class="custom-control custom-checkbox">'+
                '               <input type="checkbox" value="'+item.id+'" name="filtro"  class="filterCheckbox robotoFamily custom-control-input" id="checkbox-'+item.id+'">'+
                '               <label class="custom-control-label" style="line-height:24px" for="checkbox-'+item.id+'">'+item.name+'</label>'+
                '           </div>'+
                '       </div><br />';
        });
        filterCategory +=
            '       </div>' +
            '	</div>' +
            '</div>';
    });
	return filterCategory;
}


function genereteListLayer(){
    var layerCategory = '';
    categories.filter(function (categoria) {
        return categoria.type === 'LAYER';
    }).forEach(function(item) {

        layerCategory += 	'					<div style="border-bottom: 1px solid #E0E0E0;" class="card">' +
            '						<div style="border: 0;" class="card-header" id="headingCardLayer'+ item.id +'">' +
            '							<h5 class="mb-0">' +
            '								<button style="background-color: #fff;" class="btn btn-link" data-toggle="collapse" data-target="#collapsLayere'+ item.id  +'" aria-expanded="true" aria-controls="collapseOne">' +
            '									<div class="robotoFamily" style="display: inline-block; text-overflow: ellipsis; overflow: hidden; width: 200px; height: 18px; white-space: nowrap;" title="'+ item.name +'">'+ item.name +' </div><i class="pull-right fa fa-angle-right" aria-hidden="true"></i>' +
            '								</button>' +
            '							</h5>' +
            '						</div>' +
            '						<div id="collapsLayere'+ item.id  +'" class="collapse" aria-labelledby="headingCardLayer'+ item.id +'" data-parent="#accordion">' +
            '							<div class="card-body">';

        groupLayer.filter(function(layer) {
            return layer.category.name == item.name;
        }).forEach(function(layer) {
            layerCategory += 	'				<div class="form-check">' +
                '                                   <div class="custom-control custom-checkbox">' +
                '									    <input class="custom-control-input form-check-input layerCheckbox" name="layerCheckbox" type="checkbox" value="'+ layer.id +'" id="layer'+ layer.id +'" />' +
                '    									<label class="custom-control-label form-check-label robotoFamily" for="layer'+ layer.id +'">' +
                '										    '+ layer.name +
                '									    </label>' +
                '                                   </div>' +
                '								</div>';
        });

        layerCategory +=	'							</div>' +
            '						</div>' +
            '					</div>';

    });
    return layerCategory;
}

// Choropleth map

// Retorna o valor da regiao em Float, passando o geoCode e o Array de dados
function matchKey(datapoint, key_variable){
    return(key_variable[datapoint]);
};

getGradientColor = function(start_color, end_color, percent) {
   // strip the leading # if it's there
   start_color = start_color.replace(/^\s*#|\s*$/g, '');
   end_color = end_color.replace(/^\s*#|\s*$/g, '');

   // convert 3 char codes --> 6, e.g. `E0F` --> `EE00FF`
   if(start_color.length == 3){
     start_color = start_color.replace(/(.)/g, '$1$1');
   }

   if(end_color.length == 3){
     end_color = end_color.replace(/(.)/g, '$1$1');
   }

   // get colors
   var start_red = parseInt(start_color.substr(0, 2), 16),
       start_green = parseInt(start_color.substr(2, 2), 16),
       start_blue = parseInt(start_color.substr(4, 2), 16);

   var end_red = parseInt(end_color.substr(0, 2), 16),
       end_green = parseInt(end_color.substr(2, 2), 16),
       end_blue = parseInt(end_color.substr(4, 2), 16);

   // calculate new color
   var diff_red = end_red - start_red;
   var diff_green = end_green - start_green;
   var diff_blue = end_blue - start_blue;

   diff_red = ( (diff_red * percent) + start_red ).toString(16).split('.')[0];
   diff_green = ( (diff_green * percent) + start_green ).toString(16).split('.')[0];
   diff_blue = ( (diff_blue * percent) + start_blue ).toString(16).split('.')[0];

   // ensure 2 digits by color
   if( diff_red.length == 1 ) diff_red = '0' + diff_red
   if( diff_green.length == 1 ) diff_green = '0' + diff_green
   if( diff_blue.length == 1 ) diff_blue = '0' + diff_blue

   return '#' + diff_red + diff_green + diff_blue;
};

// get color depending on population density value
function getColor(d) {
	if(d >= minValue && d <= midValue){
		return getGradientColor('#e5c75a', '#f69b3f', (d - minValue) / (midValue - minValue));
	}else if (d > midValue && d <= maxValue) {
		return getGradientColor('#f69b3f', '#ff6745', (d - midValue) / (maxValue - midValue));
	}
	return '#CBD8DD';
}

// Retorna a porcetatem para a barra de range de valores
function getPercent(d) {
	return ((d - minValue) / (maxValue - minValue)) * 100;
}

// pinta a regiao
function style_1(feature) {
	return {
		fillColor: getColor(matchKey(feature.geoCode, indicatorArray)),
		weight: (feature.geoCode.toString().length > 2) ? 0.1 : 1,
		opacity: 1,
		color: '#146678',
		fillOpacity: 0.8
	};
}
// Choropleth map

// Funcao para retornar o menor valor de um array
Array.min = function(array) {
	return Math.min.apply(Math, array);
};

// Funcao para retornar o maior valor de um array
Array.max = function(array) {
	return Math.max.apply(Math, array);
};

// Funcao para setar os valores das variaveis com base no json
function setJsonVars(json) {
	var values = Object.values(json);
	minValue = Array.min(values);
	maxValue = Array.max(values);
	midValue = (maxValue + minValue) / 2;
}

// MenuMapControl
function menuMapControl(){
    let pos = ($(".menu--popup").css('right') == '0px') ? '-327px' : '0';
    $(".menu--popup").animate({right: pos}, 800);
    let pos2 = ($(".leaflet-right.leaflet-bottom").css('right') == '0px') ? '340px' : '0';
    $(".leaflet-right.leaflet-bottom").animate({right: pos2}, 800);
    $('#seta-sidebar').toggle();
}

function rangeMap(select){
    console.log(select.value);
    switch(select.value) {
        case "3":
            makeChart(3);
            break;
        case "2":
            makeChart(2);
            break;
        case "1":
            makeChart(1);
            break;
    }
}

function cleanMap(){
	map.removeLayer(group);
	if (typeof(indicadorMapControl) != "undefined") {
		indicadorMapControl.remove(map);
        filtroMapControl.remove(map);
		rangeBarControl.remove(map);
		mapControl.remove(map);
	}
}

function emptyMap(){
	indicatorArray = new Array();
    var route1Layer = L.geoJson(estado,
	{
		style: style_1

	});
	group.addLayer(route1Layer);
	map.addLayer(group);
}

// Set no mapa arquivo de regiões, função de popup nas regiões
function makeChart(rangeMap2) {

	var geoJson;
	switch(rangeMap2) {
		case 2:
			geoJson = municipio;
			indicatorArray = municipioArray;
		break;
		case 1:
			geoJson = estado;
			indicatorArray = estadoArray;
		break;
	}

	setJsonVars(indicatorArray);

	var route1Layer = L.geoJson(geoJson,
	{
		style: style_1,
		onEachFeature: function (feature, layer) {
			if (typeof indicatorArray[feature.geoCode] !== "undefined") {

			    var popupValue = '<div onclick="modalDetail(\''+feature.geoCode+'\')">' +
                    '<p id="box_'+feature.geoCode+'" class="title-popup">'+feature.properties.name+'</p>' +
                    '<p id="both" style="margin-bottom: 0">'+arrayCompleto[0].name.value+'</p>' +
                    '<p style="font-size: 16px; margin-top: 0; font-weight: bold">'+getFormattedPrintValue(arrayCompleto[0].name.typePresentation, indicatorArray[feature.geoCode])+'</p>' +
                    '<div style="text-align: center">';

			    var filtrosAplicados = filtrosPesquisados.get(parseInt(feature.geoCode));
			    if(filtrosAplicados !== undefined){
                    filtrosAplicados.forEach(function (filterName) {
                        popupValue += '<button class="button-popup">'+filterName+'</button></br>';
                    });
                }
			    popupValue += '</div> </div>';

				layer.bindPopup(popupValue);

				layer.on('mouseover', function (e) {
					var x = document.getElementsByClassName("range-bar-mark")[0];
                    x.style.left = getPercent(indicatorArray[feature.geoCode])*0.92+"%";
					x.style.display = "inline";
					$("#box"+feature.geoCode).toggle();
				});
				layer.on('mouseout', function (e) {
					var x = document.getElementsByClassName("range-bar-mark")[0];
					x.style.display = "none";
                    $("#box"+feature.geoCode).toggle();
				});
			} else {
                var popupValue = '<div>' +
                    '<p id="box_'+feature.geoCode+'" class="title-popup">'+feature.properties.name+'</p>' +
                    '<p id="both" style="margin-bottom: 0">'+arrayCompleto[0].name.value+'</p>' +
                    '<p style="font-size: 16px; margin-top: 0; font-weight: bold">Não há dados sobre a região selecionada nesse período.</p>' +
                    '<div style="text-align: center">' +
                    '</div> </div>';
				layer.bindPopup(popupValue);
            }
		}
	});

	$("#loading").hide();

	group.addLayer(route1Layer);
	map.addLayer(group);

	function getFormattedPrintValue(typePresentation, valueToShow){
	    if(typePresentation !== null && typePresentation.display.includes("$")){
	        return typePresentation.display + valueToShow.toLocaleString('pt-br', {minimumFractionDigits: 2});
        } else {
            return valueToShow.toLocaleString('pt-br');
        }
    }
}

function updateOpacity(value) {
    group.setStyle({fillOpacity:value});
}

function setLayer(json){
	json.forEach(function(item) {
		switch(item.type){
			case "MARKER":
				var latlong = JSON.parse(item.geoJson);
				var marker = new L.Marker(latlong);
				let desc = '';
				desc += ((typeof item.name === 'string' || item.name instanceof String) && item.name.length !== 0)
								? "Nome: " + item.name + "<br />" : '';
				desc += ((typeof item.description === 'string' || item.description instanceof String) && item.description.length !== 0)
								? "Descrição: " + item.description + "<br />" : '';
				desc += ((typeof item.source === 'string' || item.source instanceof String) && item.source.length !== 0)
								? "Fonte: " + item.source + "<br />" : '';
				desc += ((typeof item.note === 'string' || item.note instanceof String) && item.note.length !== 0)
								? "Notas: " + item.note + "<br />" : '';
				marker.bindPopup(desc);
  				markers.addLayer(marker);
			break;
			case "CIRCLE":
				var circle = JSON.parse(item.geoJson);
				L.circle(circle.latlong, {
					color: 'red',
					fillColor: '#f03',
					fillOpacity: 0.5,
					radius: circle.radius
				}).addTo(mymap);

			break;
			case "POLYGON":
				var polygon = JSON.parse(item.geoJson);
				L.polygon(polygon).addTo(mymap);
			break;
			case "ICON":
                var latlong = JSON.parse(item.geoJson);
				var greenIcon = L.icon({
					iconUrl: 'leaf-green.png',
					shadowUrl: 'leaf-shadow.png',

					iconSize:     [38, 95], // size of the icon
					shadowSize:   [50, 64], // size of the shadow
					iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
					shadowAnchor: [4, 62],  // the same for the shadow
					popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
                });
                var marker = new L.Marker(latlong, {icon: greenIcon});
  				markers.addLayer(marker);
			break;
		}
	});
	map.addLayer(markers);
}

function setYears(nameId) {
    var yearsOptions = '';
    $.ajax({
        url: '/api/yearsDistinct?&nameId.equals='+nameId,
        dataType: 'json',
        async: false,
        success: function (data) {
            data.forEach(function(item) {
                yearsOptions += '<option value="' + item.id +'">' + item.date + '</option>';
            });
        }
    });
    return yearsOptions;
}

function changeYear(yearOption){
	map.removeLayer(group);

	var urlQuery = '';
	if(filtrosId.size === 0){
		urlQuery += '/api/indicators?yearId.equals='+yearOption.value+'&nameId.equals='+indicadorAtual;
	} else {
		var vetor = new Array();
		filtrosId.forEach(function (filtro) {
			vetor.push(filtro);
		});
		var stringResult = vetor.join(',');
		urlQuery += '/api/indicatorsFilter?yearId.equals='+yearOption.value+'&filtersId.in='+stringResult+'&nameId.equals='+indicadorAtual;

		$.ajax({
			url: '/api/regionsWithFilter?yearId.equals='+yearOption.value+'&filtersId.in='+stringResult,
			dataType: 'json',
			success: function (dataJson) {
				filtrosPesquisados = new Map();
				dataJson.forEach(function (dto) {
					var contains = filtrosPesquisados.get(dto.geocode);
					if(contains === undefined){
						filtrosPesquisados.set(dto.geocode, []);
						contains = filtrosPesquisados.get(dto.geocode);
					}

					contains.push(dto.filtername);
				});
			}
		});
	}

	$.ajax({
		url: urlQuery,
		dataType: 'json',
		success: function (dataJson) {

			if (dataJson.length < 1) {
				$("#loading").hide();
				emptyMap();
			}
			else {
				arrayCompleto = dataJson;
				municipioArray = new Array();
				estadoArray = new Array();

				dataJson.forEach(function (indicator) {

					if (indicator.region.typeRegion == "MUNICIPIO") {
						municipioArray[indicator.region.geoCode.toString()] = indicator.value;
					} else if (indicator.region.typeRegion == "ESTADO") {
						estadoArray[indicator.region.geoCode.toString()] = indicator.value;
						return;
					}
				});

				itensSelect[0] = '<option value="1">Estado</option>';
				itensSelect[1] = '<option value="2">Municipio</option>';

				if (estadoArray.length > 0) {
					if (municipioArray.length < 1){
						delete itensSelect[1];
					}
					makeChart(1);
				} else if (municipioArray.length > 0) {
					delete itensSelect[0];
					makeChart(2);
				} else {
					console.error("Empty DATAJSON")
				}
			}
		}
	});
}

function limparMap() {
	location.reload();
}

function makeMap() {

	$("#loading").hide();

	emptyMap();

	//Functions to either disable (onmouseover) or enable (onmouseout) the map's dragging
	function controlEnter(e) {
		map.dragging.disable();
        map.scrollWheelZoom.disable();
	}
	function controlLeave() {
		map.dragging.enable();
		map.scrollWheelZoom.enable();
	}

	// Menu Map control
	var menuMapControl = L.control({position: 'topright'});
	menuMapControl.onAdd = function(map) {
				this._div = L.DomUtil.create('div', 'leaflet-bar easy-button-container menuMapControl');
				this._div.innerHTML = '<div>' +
									  '<div style="border-radius:4px; background-color: rgba(255,255,255,0.9); color: #59C9A8; font-size: 28px; padding: 1px 10px;" onclick="menuMapControl()"><i class="fa fa-sliders"></i></div>' +
									  '	<div class="menu--popup">' +
									  '		<ul class="nav nav-tabs" id="myTab" role="tablist">' +
									  '			<li class="nav-item">' +
									  '				<a class="nav-link active robotoFamily" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">INDICADORES</a>' +
									  '			</li>' +
									  '			<li class="nav-item">' +
									  '				<a class="nav-link robotoFamily" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">FILTROS</a>' +
									  '			</li>' +
									  '			<li class="nav-item">' +
									  '				<a class="nav-link robotoFamily" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">CAMADAS</a>' +
									  '			</li>' +
									  '		</ul>' +
									  '		<div class="tab-content" id="myTabContent">' +
									  '			<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">' +
									  '				<div id="accordion">' +
														genereteListIndicators() +
									  '				</div>' +
									  '			</div>' +
									  '			<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">' +
									  '				<div id="accordionFilter">' +
                                                        generateListFilters() +
									  '				</div>'+
                                      '             <div id="selectedIndicator" style="text-align: center;"><span> Não há indicador selecionado</span></div>' +
									  '			</div>' +
									  '			<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">' +
														genereteListLayer() +
												'</div>' +
									  '		</div>' +
									  '		<span id="seta-sidebar" onclick="menuMapControl()" style="position:absolute;left: -40px;top: 0;color:white;background-color: #59C9A8;padding: 4px 12px;border-radius:4px 0 0 4px;font-size: 22px;"><span class="fa fa-chevron-right"> </span></span>' +
                                      '		<div id="footer-sidebar-filtros">' +
                                      '		    <div class="pull-left" style="width:60%;min-height:1px;margin-top:20px">' +
                                      ' 		    <div class="indicador" style="text-align:center"></div>' +
                                      '     		<div class="filtro" style="margin: 10px 45px 0 0;"></div>' +
                                      '		    </div>' +
                                      '		    <div class="pull-left" style="width:40%;padding-right:20px">' +
									  '    		    <button id="submitFormIndicator" disabled style="margin: 15px 15px 0; background-color: #146678; color: #fff;" class="btn pull-right robotoFamily">Aplicar</button><br>' +
                                      '       		<button id="clearFormIndicator" style="margin: 0 5px; background-color: #f5f6f6; color: #146678;" class="btn pull-right robotoFamily">Limpar&nbsp;<span class="fa fa-trash-o"></span></button>' +
                                      '		    </div>' +
                                      '		</div>' +
									  '	</div>' +
									  '</div>';


					L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);

				return this._div;jhiTranslate="global.menu."
	}
	menuMapControl.addTo(map);

	document.getElementsByClassName("menuMapControl")[0].onmouseover = controlEnter;
	document.getElementsByClassName("menuMapControl")[0].onmouseout = controlLeave;

	$('#accordion .card').on('hidden.bs.collapse', function () {
		$(this).find( "i" ).removeClass("fa-angle-down");
		$(this).find( "i" ).addClass("fa-angle-right");
	});

	$('#accordion .card').on('shown.bs.collapse', function () {
		$(this).find( "i" ).addClass("fa-angle-down");
		$(this).find( "i" ).removeClass("fa-angle-right");
	});

	$('#accordionFilter .card').on('hidden.bs.collapse', function () {
		$(this).find( "i" ).removeClass("fa-angle-down");
		$(this).find( "i" ).addClass("fa-angle-right");
	});

	$('#accordionFilter .card').on('shown.bs.collapse', function () {
		$(this).find( "i" ).addClass("fa-angle-down");
		$(this).find( "i" ).removeClass("fa-angle-right");
	});

	$('input[type=radio][name=indicator]').change(function() {
		indicadorAtual = this.value;
        indicadorSelecionado = indicadorAtual;
        $('#selectedIndicator').html("<span id='msgIndicadorSelecionado'>Indicador <b>"+$(this.nextElementSibling).text().trim()+"</b> selecionado</span>");
		$('#submitFormIndicator').prop('disabled', false);
	});

	$('input[type=checkbox][name=filtro]').change(function () {
        if($('.layerCheckbox:checked').val() !== undefined){
            $('#submitFormIndicator').prop('disabled', false);
        }
    });

	$('input[type=checkbox][name=layerCheckbox]').change(function () {
        if($('input[type=checkbox][name=layerCheckbox]:checked').length > 0)
            $('#submitFormIndicator').prop('disabled', false);
        else if($('input[type=radio][name=indicator]:checked').val() === undefined)
            $('#submitFormIndicator').prop('disabled', true);
    });

	// Method to clear the lateral form
	$('#clearFormIndicator').click(function () {
        $('.menu--popup').find(':input').each(function () {
            if(this.type === 'checkbox' || this.type === 'radio'){
                this.checked = false;
            }
        });

        indicadorAtual = undefined;
        $('#selectedIndicator').html("<span> Não há indicador selecionado</span>");
        $('#submitFormIndicator').prop('disabled', true);
    });

    function loadResultMap(urlQuery) {
        $.ajax({
            url: urlQuery,
            dataType: 'json',
            success: function (dataJson) {

                if (dataJson.length < 1) {
                    $("#loading").hide();
                    emptyMap();
                }
                else {
                    arrayCompleto = dataJson;
                    municipioArray = new Array();
                    estadoArray = new Array();

                    //console.log(dataJson);

                    dataJson.forEach(function (indicator) {

                        if (indicator.region.typeRegion == "MUNICIPIO") {
                            municipioArray[indicator.region.geoCode.toString()] = indicator.value;
                        } else if (indicator.region.typeRegion == "ESTADO") {
                            estadoArray[indicator.region.geoCode.toString()] = indicator.value;
                            return;
                        }
                    });

                    itensSelect[0] = '<option value="1">Estado</option>';
                    itensSelect[1] = '<option value="2">Municipio</option>';

                    if (estadoArray.length > 0) {
						if (municipioArray.length < 1){
							delete itensSelect[1];
						}
						makeChart(1);
					} else if (municipioArray.length > 0) {
                        delete itensSelect[0];
                        makeChart(2);
                    } else {
                        console.log("Empty DATAJSON")
                    }

                    // html do SELECT
                    var selectRangeMapHtml = '<div class="input-group input-group-sm mb-3">' +
                        '<div class="input-group-prepend">' +
                        '<span style="margin-right:5px;" class="input-group-text fa fa-map input-leaflet" id="basic-addon1"></span>' +
                        '</div>' +
                        '<select onchange="rangeMap(this)" style="border-radius: 0 5px 5px 0; border-left:0;">';

                    itensSelect.forEach(function (iten) {
                        selectRangeMapHtml += iten;
                    });

                    selectRangeMapHtml += '</select>' +
                        '</div>';
                    // html do SELECT

                    // indicador Map control
                    indicadorMapControl = L.control({position: 'topright'});
                    indicadorMapControl.onAdd = function (map) {
                        this._div = L.DomUtil.create('div', 'indicadorMapControl');
                        this._div.innerHTML = '<div style="cursor: pointer;color:#59C9A8; font-size:16px;" onclick="infoAboutIndicator()" class="" id="indicadorMapControl" title="Menu">' +
                            '<span class="robotoFamily">' +
                            dataJson[0].name.value + ' <span style="background-color: #59C9A8; padding:3.5px 6.5px; color: white; border-radius: 4px; font-size:12px; position:relative; top:-2.5px;" class="fa fa-info"></span>' +
                            '</span></div>';

                        $("#footer-sidebar-filtros .indicador").html(this._div.innerHTML);

                        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
                        L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
                        L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

                        return this._div;
                    }
                    indicadorMapControl.addTo(map);

                    // filtro Map control
                    filtroMapControl = L.control({position: 'topright'});
                    filtroMapControl.onAdd = function (map) {
                        this._div = L.DomUtil.create('div', 'filtroMapControl');

                        if (filtrosId.size !== 0) {
                            this._div.innerHTML = loadFiltrosSelecionados();
                        }

                        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
                        L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
                        L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

                        return this._div;
                    }
                    filtroMapControl.addTo(map);

                    var sliderOpacidade = '<div style="background-color: white;border-radius: 10px;padding: 2px 6px; color: #bdbdbd; width: 156px;">' +
                                          '<i class="fa fa-adjust"></i>&nbsp;' +
                                          '0 <input id="sliderOpacidade" type="range" min="0" max="1" step="0.1" value="0.8" onchange="updateOpacity(this.value)"> 100' +
                                          '</div>';

                    // range bar Map control
                    rangeBarControl = L.control({position: 'bottomright'});
                    rangeBarControl.onAdd = function (map) {
                        this._div = L.DomUtil.create('div', 'rangeBarControl');
                        this._div.innerHTML = '' +
                            '<i class="fa fa-minus" aria-hidden="true"></i><div class="range-bar">' +
                            '<span class="range-bar-mark"></span>' +
                            '</div><i class="fa fa-plus" aria-hidden="true"></i>' +
                            '<select onchange="changeYear(this)" class="select-year">' +
                                setYears(indicadorAtual) +
                            '</select>' + sliderOpacidade;


                        return this._div;
                    }
                    rangeBarControl.addTo(map);
                    document.getElementsByClassName("rangeBarControl")[0].onmouseover = controlEnter;
                    document.getElementsByClassName("rangeBarControl")[0].onmouseout = controlLeave;

                    // select Map control
                    mapControl = L.control({position: 'bottomleft'});
                    mapControl.onAdd = function (map) {
                        this._div = L.DomUtil.create('div', 'mapControl');
                        this._div.innerHTML = selectRangeMapHtml;

                        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);

                        return this._div;
                    }
                    mapControl.addTo(map);

                    document.getElementsByClassName("mapControl")[0].onmouseover = controlEnter;
                    document.getElementsByClassName("mapControl")[0].onmouseout = controlLeave;
                }
            }
        });
    }

    $('#submitFormIndicator').click(function() {

		itensSelect = new Array();

		filtrosId = new Set();
        $('#accordionFilter input:checkbox:checked').map(function(){
            filtrosId.add(parseInt($(this).val()));
        });

        layersId = new Set();
        $('input[name=layerCheckbox]:checkbox:checked').map(function(){
            layersId.add(parseInt($(this).val()));
        });

		markers.clearLayers();
        if(layersId.size != 0){
            layersId.forEach(function (layerId) {
                var strQuery ="?groupId.equals="+layerId+"&";
				$.ajax({
					url: '/api/layers'+strQuery,
					dataType: 'json',
					success: function (data) {
						setLayer(data);
					}
				});
            });
        }

        if(indicadorAtual !== undefined){
            cleanMap();

            group = new L.FeatureGroup();
            $("#loading").show();

            var urlQuery = '';
            if(filtrosId.size === 0){
                urlQuery += '/api/indicators?&nameId.equals='+indicadorAtual;
                loadResultMap(urlQuery);

            } else {
                var vetor = new Array();
                filtrosId.forEach(function (filtro) {
                    vetor.push(filtro);
                });
                var stringResult = vetor.join(',');
                urlQuery += '/api/indicatorsFilter?&filtersId.in='+stringResult+'&nameId.equals='+indicadorAtual;

                $.ajax({
                    url: '/api/regionsWithFilter?&filtersId.in='+stringResult,
                    dataType: 'json',
                    success: function (dataJson) {
                        filtrosPesquisados = new Map();
                        dataJson.forEach(function (dto) {
                            var contains = filtrosPesquisados.get(dto.geocode);
                            if(contains === undefined){
                                filtrosPesquisados.set(dto.geocode, []);
                                contains = filtrosPesquisados.get(dto.geocode);
                            }

                            contains.push(dto.filtername);
                        });

                        loadResultMap(urlQuery);
                    }
                });
            }
        }

		$(document).ajaxComplete(function() {
			// limpar Map control
			if (typeof(limparMapControl) != "undefined") {
				limparMapControl.remove(map);
			}
			limparMapControl = L.control({position: 'topright'});
			limparMapControl.onAdd = function (map) {
				this._div = L.DomUtil.create('div', 'limparMapControl');
				this._div.innerHTML = '<div style="cursor: pointer;color:#FFFFFF; font-size:14px;" onclick="limparMap()" class="" id="limparMapControl" title="Menu">' +
					'<span style="text-decoration: underline;">' +
					'limpar <span class="fa fa-trash-o"></span>' +
					'</span></div>';

				L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
				L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
				L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

				return this._div;
            }
            
			limparMapControl.addTo(map);
		});


	});

};

function infoAboutIndicator() {

    $('#tituloModal').html("INFORMAÇÕES SOBRE INDICADORES");
    $('#idInfoIndicador').val(indicadorSelecionado);

    $.ajax({
        url: '/api/namesPublic/'+indicadorSelecionado,
        dataType: 'json',
        success: function (dataJson) {
            // create DTO with api name and filters

            var conteudoModal =
                '<div>' +
                '   <div>' +
                '       <div style="margin-bottom: 10px">' +
                '           <label style="margin-right: 5px">' + dataJson.value + ' </label>' +
                // '          <button class="btn btn-outline-info" onclick="loadGraphic(\''+dataJson.name.value+'\')">Ver gráfico</button>' +
                '       <div style="margin-top: 10px" class="infoIndicatorSnippet">' + dataJson.description + '</div>' +
                '       <a href="/api/indicators?&nameId.equals='+indicadorSelecionado+'" download> Download</a>' +
                '       <a href="#" class="mostrarMais"> Ler mais</a>' +
                '   </div>';
            if (filtrosId.size !== 0) {
                conteudoModal +=
                    '<div id="containerDescricaoFiltros">';
                $.ajax({
                    url: '/api/filtersDTO?&id.in='+Array.from(filtrosId).join(','),
                    dataType: 'json',
                    success: function (dataJson) {
                        dataJson.forEach(function (filterInfo) {
                            conteudoModal +=
                                '<div class="card">' +
                                '   <div id="headingFilterInfo' + filterInfo.id + '" data-toggle="collapse" data-target="#collapseFilterInfo' + filterInfo.id + '" aria-expanded="false" aria-controls="collapseFilterInfo' + filterInfo.id + '">' +
                                '       <label class="iconInfoFilter">' + filterInfo.name + '</label>' +
                                '       <i class="fa fa-angle-down"></i>' +
                                '   </div>' +
                                '   <div id="collapseFilterInfo' + filterInfo.id + '" class="collapse" aria-labelledby="headingFilterInfo' + filterInfo.id + '" data-parent="#containerDescricaoFiltros">';
                            conteudoModal +=
                                '       <div class="card-body" style="padding: 0 0 1rem 1rem">' + filterInfo.description + '</div>' +
                                '   </div>' +
                                '</div>';
                        });
                        conteudoModal += '</div> ' +
                            '</div>';
                        $('#modalBody').html(conteudoModal);
                        $('#exampleModal').modal('show');

                        $('.mostrarMais').click(function () {
                            if($('.infoIndicatorSnippet').css('height') != '30px'){
                                $('.infoIndicatorSnippet').stop().animate({height: '30px'}, 200);
                                $(this).text('Ler mais');
                            } else {
                                $('.infoIndicatorSnippet').css({height:'100%'});
                                var xx = $('.infoIndicatorSnippet').height();
                                $('.infoIndicatorSnippet').css({height:'30px'});
                                $('.infoIndicatorSnippet').stop().animate({height: xx}, 400);
                                $(this).text('Ler menos');
                            }
                        });

                        $('#containerDescricaoFiltros .card').on('hidden.bs.collapse', function () {
                            $(this).find( "i" ).removeClass("fa-angle-down");
                            $(this).find( "i" ).addClass("fa-angle-right");
                        });

                        $('#containerDescricaoFiltros .card').on('shown.bs.collapse', function () {
                            $(this).find( "i" ).addClass("fa-angle-down");
                            $(this).find( "i" ).removeClass("fa-angle-right");
                        });
                    }
                });
            } else {
                conteudoModal += '</div>';
                $('#modalBody').html(conteudoModal);
                $('#exampleModal').modal('show');
            }

            $('.mostrarMais').click(function () {
                if($('.infoIndicatorSnippet').css('height') != '30px'){
                    $('.infoIndicatorSnippet').stop().animate({height: '30px'}, 200);
                    $(this).text('Ler mais');
                } else {
                    $('.infoIndicatorSnippet').css({height:'100%'});
                    var xx = $('.infoIndicatorSnippet').height();
                    $('.infoIndicatorSnippet').css({height:'30px'});
                    $('.infoIndicatorSnippet').stop().animate({height: xx}, 400);
                    $(this).text('Ler menos');
                }
            });
        }
    });
}

function loadFiltrosSelecionados(){
    var filtrosSelecionados =
        '<div class="iconFilter float-right" onclick="infoAboutIndicator()" id="filtroMapControl" title="filtro">' +
            '<span class="button-state state-get-center get-center-active robotoFamily">'+
                '<span class="fa fa-info"></span> Filtros selecionados' +
            '</span>'+
        '</div>';

    $("#footer-sidebar-filtros .filtro").html(filtrosSelecionados);

    return filtrosSelecionados;
}


/*
    Metodos para verificar os dados do usuario logado
*/
function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};
