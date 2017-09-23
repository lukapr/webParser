import React, {Component} from 'react';
// import Config from 'config';
import {DataTable, TableHeader} from 'react-mdl';

class ConfigTable extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const result = [];
        this.props.configs.forEach(function (config) {
            result.push({
                name: config.name, description: config.description, link: config.link
            })
        });
        return (
            result.length > 0 ?
            <DataTable
                shadow={0}
                rows={result}
            >
                <TableHeader name="name" tooltip="The name" weid>Name</TableHeader>
                <TableHeader name="description" tooltip="The description">Description</TableHeader>
                <TableHeader name="link" tooltip="The link">Link</TableHeader>
            </DataTable>
                : null
        )
    }
}

export default ConfigTable;