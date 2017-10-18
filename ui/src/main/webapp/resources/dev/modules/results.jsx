import React, {Component, PropTypes} from 'react';
import {notify} from 'react-notify-toast';

import 'jquery';
import 'jqwidgets-framework/jqwidgets/jqx-all'
import JqxPivotGrid from 'jqwidgets-framework/jqwidgets-react/react_jqxpivotgrid';
import jqx from 'jqwidgets-framework/jqwidgets/jqxchart.core'

import request from 'superagent';

export default class Results extends Component {
    constructor(props) {
        super(props);
        this.state = {
            results: []
        };
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.load) {
            this.loadFromServer();
        }
    };

    loadFromServer = () => {
        request.get("results/")
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during getting configs: ' + err, "error");
                } else {
                    // notify.show('yay got ' + JSON.stringify(res.body), "success");
                    this.setState({results: res.body})
                }
            })
    };


    render() {
        // Prepare Sample Data
        const data = new Array();

        const countries =
            [
                'Germany', 'France', 'United States', 'Italy', 'Spain', 'Finland', 'Canada', 'Japan', 'Brazil', 'United Kingdom', 'China', 'India', 'South Korea', 'Romania', 'Greece'
            ];

        const dataPoints =
            [
                '2.25', '1.5', '3.0', '3.3', '4.5', '3.6', '3.8', '2.5', '5.0', '1.75', '3.25', '4.0'
            ];

        for (let i = 0; i < countries.length * 2; i++) {
            let row = {};
            const value = parseFloat(dataPoints[Math.round((Math.random() * 100)) % dataPoints.length]);
            row['country'] = countries[i % countries.length];
            row['value'] = value;
            data[i] = row;
        }

        // Create a Data Source and DataAdapter
        const source =
            {
                localdata: data,
                datatype: 'array',
                datafields:
                    [
                        { name: 'country', type: 'string' },
                        { name: 'value', type: 'number' }
                    ]
            };

        const dataAdapter = new jqx.dataAdapter(source);
        dataAdapter.dataBind();

        // Create a Pivot Data Source from the DataAdapter
        const pivotDataSource = new jqx.pivot(
            dataAdapter,
            {
                pivotValuesOnRows: false,
                rows: [{ dataField: 'country', width: 190 }],
                columns: [],
                values:
                    [
                        { dataField: 'value', width: 216, 'function': 'min', text: 'cells left alignment', formatSettings: { align: 'left', prefix: '', decimalPlaces: 2 } },
                        { dataField: 'value', width: 216, 'function': 'max', text: 'cells center alignment', formatSettings: { align: 'center', prefix: '', decimalPlaces: 2 } },
                        { dataField: 'value', width: 216, 'function': 'average', text: 'cells right alignment', formatSettings: { align: 'right', prefix: '', decimalPlaces: 2 } }
                    ]
            }
        );
        return (
            <JqxPivotGrid style={{ width: 800, height: 400 }}
                          source={pivotDataSource} treeStyleRows={true}
                          autoResize={true} multipleSelectionEnabled={true}
            />
        );
    }

}