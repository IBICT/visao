// Iniciar mapa objeto sem zoom, latitude, longitude, zoom inicial
var map = L.map('map', { zoomControl:false }).setView([-15, -50], 4);

var group = new L.FeatureGroup();

var testeP, testeD, maxValue, minValue, midValue;

function modalDatail(data){
    $('#modalEstadoSigla').html(data);
    $('#modalHabitantesValor').html("3.873.743");
    $('#exampleModal').modal('show');
}

function rangeMap(select){
    map.removeLayer(group);
    console.log(select.value);
    var route1Layer = L.geoJson(
        testeP,
        {
            style: {
                fillColor: '#CBD8DD',
                weight: 1,
                opacity: 1,
                color: '#146678',
                fillOpacity: 1
            }
        });
    group = new L.FeatureGroup();
    group.addLayer(route1Layer);
    map.addLayer(group);
}

// thema do mapa, maximo zoom, legenda roda-pé
/*
L.tileLayer('http://{s}.basemaps.cartocdn.com/dark_nolabels/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: 'Map data (c) <a href="http://openstreetmap.org">OpenStreetMap</a> contributors'
}).addTo(map);
*/

// Choropleth map
function matchKey(datapoint, key_variable){
    return(parseFloat(key_variable[0][datapoint]));
};

// get color depending on population density value
function getColor(d) {
    return 	isNaN(d)                       ? '#CBD8DD' :
        d == maxValue                  ? '#ff6745' :
            d >  midValue  & d < maxValue  ? '#fc833e' :
                d == midValue                  ? '#f69b3f' :
                    d >  minValue  & d < midValue  ? '#eeb248' :
                        '#e5c75a' ;
}

function getPercent(d) {
    return 	d == maxValue                  ? 94 :
        d >  midValue  & d < maxValue  ? 75 :
            d == midValue                  ? 50 :
                d >  minValue  & d < midValue  ? 25 :
                    0.2 ;
}

function style_1(feature) {
    return {
        fillColor: getColor(matchKey(feature.id, testeD)),
        weight: 1,
        opacity: 1,
        color: '#146678',
        fillOpacity: 1
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
    var values = Object.values(json[0]);
    minValue = Array.min(values);
    maxValue = Array.max(values);
    midValue = (maxValue + minValue) / 2;
}

// MenuMapControl
function menuMapControl(){
    $( ".menu--popup" ).toggle( "slow" );
}

queue()
    .defer(d3.json, 'data-br.json')
    .defer(d3.json, 'estado.json')
    .defer(d3.json, 'MesoRegiao.json')
    .await(makeMap);

function makeMap(error, data_1,gjson_1,paises) {


    testeP = paises;
    testeD = data_1;
    setJsonVars(testeD);


    // Set no mapa arquivo de regiões, função de popup nas regiões
    var route1Layer = L.geoJson(gjson_1,
        {
            style: style_1,
            onEachFeature: function (feature, layer) {
                if (typeof data_1[0][feature.id] !== "undefined") {
                    layer.bindPopup('<p class="title-popup">'+feature.properties.name+'</p><p id="both">PIB per capta</p><p>'+data_1[0][feature.id]+'</p><button onclick="modalDatail(\''+feature.id+'\')" class="button-popup">'+feature.properties.name+'</button>');
                    layer.on('mouseover', function (e) {
                        var x = document.getElementsByClassName("range-bar-mark")[0];
                        x.style.left = getPercent(data_1[0][feature.id])+"%";
                        x.style.display = "inline";
                    });
                    layer.on('mouseout', function (e) {
                        var x = document.getElementsByClassName("range-bar-mark")[0];
                        x.style.display = "none";
                    });
                }
            }

        });//.addTo(map)

    //group.addLayer(teste);
    group.addLayer(route1Layer);
    map.addLayer(group);

    //Functions to either disable (onmouseover) or enable (onmouseout) the map's dragging
    function controlEnter(e) {
        map.dragging.disable();
    }
    function controlLeave() {
        map.dragging.enable();
    }

    // Menu Map control
    var menuMapControl = L.control({position: 'topright'});
    menuMapControl.onAdd = function(map) {
        this._div = L.DomUtil.create('div', 'leaflet-bar easy-button-container menuMapControl');
        this._div.innerHTML = menuMapHtml();

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


    // indicador Map control
    var indicadorMapControl = L.control({position: 'topright'});
    indicadorMapControl.onAdd = function(map) {
        this._div = L.DomUtil.create('div', 'indicadorMapControl');
        this._div.innerHTML = '<div style="cursor: pointer;color:#59C9A8; font-size:16px;" onclick="indicadorMapControl()" class="" id="indicadorMapControl" title="Menu">' +
            '<span class="">' +
            'PIB PER CAPTA <span style="background-color: #59C9A8; padding:3.5px 6.5px; color: white; border-radius: 4px; font-size:12px; position:relative; top:-2.5px;" class="fa fa-info"></span>' +
            '</span></div>';

        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

        return this._div;
    }
    indicadorMapControl.addTo(map);


    // filtro Map control
    var filtroMapControl = L.control({position: 'topright'});
    filtroMapControl.onAdd = function(map) {
        this._div = L.DomUtil.create('div', 'filtroMapControl');
        this._div.innerHTML = '<div style="cursor: pointer;padding:2.5px 10px; color:#00697C; background-color:#FFFFFF;border-radius: 15px;font-size:14px;" onclick="filtroMapControl()" id="filtroMapControl" title="filtro">' +
            '<span class="button-state state-get-center get-center-active">Tropical ' +
            '<span class="fa fa-check-square-o"></span>' +
            '</span></div>';

        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

        return this._div;
    }
    filtroMapControl.addTo(map);

    // filtro Map control
    var limparMapControl = L.control({position: 'topright'});
    limparMapControl.onAdd = function(map) {
        this._div = L.DomUtil.create('div', 'limparMapControl');
        this._div.innerHTML = '<div style="cursor: pointer;color:#FFFFFF; font-size:14px;" onclick="limparMapControl()" class="" id="limparMapControl" title="Menu">' +
            '<span style="text-decoration: underline;">' +
            'limpar <span class="fa fa-trash-o"></span>' +
            '</span></div>';

        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

        return this._div;
    }
    limparMapControl.addTo(map);


    // select Map control
    var mapControl = L.control({position: 'bottomleft'});
    mapControl.onAdd = function(map) {
        this._div = L.DomUtil.create('div', 'mapControl');
        this._div.innerHTML = '<div class="input-group input-group-sm mb-3">' +
            '<div class="input-group-prepend">' +
            '<span style="margin-right:5px;" class="input-group-text fa fa-map input-leaflet" id="basic-addon1"></span>' +
            '<span style="color: #59C9A8; border-right:0; background-color: #ffffff" class="input-group-text"><i class="fa fa-dot-circle-o" aria-hidden="true"></i></span>' +
            '</div>' +
            '<select onchange="rangeMap(this)" style="border-radius: 0 5px 5px 0; border-left:0;">' +
            '<option value="estado">Estado</option>' +
            '<option value="regiao">Região</option>' +
            '<option value="municipio">Municipio</option>' +
            '</select>' +
            '</div>';

        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);

        return this._div;
    }
    mapControl.addTo(map);

    document.getElementsByClassName("mapControl")[0].onmouseover = controlEnter;
    document.getElementsByClassName("mapControl")[0].onmouseout = controlLeave;
    // Map control



    var botao2 =  L.easyButton({
        id: 'botao2',  // an id for the generated button
        position: 'bottomleft',      // inherited from L.Control -- the corner it goes in
        type: 'replace',          // set to animate when you're comfy with css
        leafletClasses: true,     // use leaflet classes to style the button?
        states:[{                 // specify different icons and responses for your button
            stateName: 'get-center',
            onClick: function(button, map){
                //event.preventDefault();
                $("#search").addClass("open");
                $('#search > form > input[type="search"]').focus();
            },
            title: 'show me the middle',
            icon: 'fa-search'
        }]
    });
    botao2.addTo(map);

    var botao3 =  L.easyButton({
        id: 'botao3',  // an id for the generated button
        position: 'bottomleft',      // inherited from L.Control -- the corner it goes in
        type: 'replace',          // set to animate when you're comfy with css
        leafletClasses: true,     // use leaflet classes to style the button?
        states:[{                 // specify different icons and responses for your button
            stateName: 'get-center',
            onClick: function(button, map){

            },
            title: 'show me the middle',
            icon: 'fa-list'
        }]
    });
    botao3.addTo(map);




    // range bar Map control
    var rangeBarControl = L.control({position: 'bottomright'});
    rangeBarControl.onAdd = function(map) {
        this._div = L.DomUtil.create('div', 'rangeBarControl');
        this._div.innerHTML = '<div class="range-bar">' +
            '<span class="range-bar-mark"></span>' +
            '</div>';

        L.DomEvent.addListener(this._div, 'dblclick', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mousedown', L.DomEvent.stop);
        L.DomEvent.addListener(this._div, 'mouseup', L.DomEvent.stop);

        return this._div;
    }
    rangeBarControl.addTo(map);

};

function menuMapHtml(){
    return '<div>' +
        '<span style="padding:6px 8px; border-radius:4px; background-color: #fff; color: #59C9A8;" onclick="menuMapControl()"> <span class="fa fa-bars"> </span></span>' +
        '	<div class="menu--popup">' +
        '		<ul class="nav nav-tabs" id="myTab" role="tablist">' +
        '			<li class="nav-item">' +
        '				<a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">INDICADORES</a>' +
        '			</li>' +
        '			<li class="nav-item">' +
        '				<a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">FILTROS</a>' +
        '			</li>' +
        '			<li class="nav-item">' +
        '				<a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">HISTÓRICO</a>' +
        '			</li>' +
        '		</ul>' +
        '		<div class="tab-content" id="myTabContent">' +
        '			<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">' +
        '				<div id="accordion">' +
        '					<div style="border-bottom: 1px solid #E0E0E0;" class="card">' +
        '						<div style="border: 0;" class="card-header" id="headingOne">' +
        '							<h5 class="mb-0">' +
        '								<button style="background-color: #fff;" class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">' +
        '									Economia <i class="pull-right fa fa-angle-right" aria-hidden="true"></i>' +
        '								</button>' +
        '							</h5>' +
        '						</div>' +
        '						<div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">' +
        '							<div class="card-body">' +
        '								<div class="form-check">' +
        '									<input class="form-check-input" type="checkbox" value="" id="indicador1" />' +
        '									<label class="form-check-label" for="indicador1">' +
        '										PIB PER CAPTA' +
        '									</label>' +
        '								</div>' +
        '							</div>' +
        '						</div>' +
        '					</div>' +
        '					<div style="border-bottom: 1px solid #E0E0E0;" class="card">' +
        '						<div style="border: 0;" class="card-header" id="headingTwo">' +
        '							<h5 class="mb-0">' +
        '								<button style="background-color: #fff;" class="btn btn-link collapsed collapsed-menu-button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">' +
        '									Saúde <i class="pull-right fa fa-angle-right" aria-hidden="true"></i>' +
        '								</button>' +
        '							</h5>' +
        '						</div>' +
        '						<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">' +
        '							<div class="card-body">' +
        '								<div class="form-check">' +
        '									<input class="form-check-input" type="checkbox" value="" id="indicador11" />' +
        '									<label class="form-check-label" for="indicador11">' +
        '										Quantidade de Médicos' +
        '									</label>' +
        '								</div>' +
        '							</div>' +
        '						</div>' +
        '					</div>' +
        '				</div>' +
        '				<select style=" margin: 10px; color: #146678; border: 0; border-bottom: 1px solid #E0E0E0;" class="" id="inlineFormCustomSelectPref">' +
        '					<option selected>2018</option>' +
        '					<option value="1">2017</option>' +
        '					<option value="2">2016</option>' +
        '					<option value="3">2015</option>' +
        '				</select>' +
        '				<select style=" margin: 10px; color: #146678; border: 0; border-bottom: 1px solid #E0E0E0;" class="" id="inlineFormCustomSelectPref">' +
        '					<option selected>Estados</option>' +
        '					<option value="1">DF</option>' +
        '				</select>' +
        '			</div>' +
        '			<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>' +
        '			<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>' +
        '		</div>' +
        '		<span onclick="menuMapControl()" style="position:relative; left:-31px; top: -438px;; color:white; background-color: #59C9A8; padding: 6px 8px;border-radius:4px 0 0 4px;"><span class="fa fa-times"> </span></span>' +
        '		<hr style="background-color:#146678; height: 1px;" />' +
        '		<button disabled style="background-color: #146678; color: #fff;" class="btn pull-right">Aplicar</button>' +
        '	</div>' +
        '</div>';
}
