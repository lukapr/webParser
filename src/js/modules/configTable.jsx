import React, {Component} from 'react';
import Config from './config';
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow,
} from 'material-ui/Table';

class ConfigTable extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const result = [];
        this.props.configs.forEach(function (config) {
            result.push(<Config config={config} key={config.name}/>
            )
        });
        return (
            <div style={{width: '100%'}}>
                <Table>
                    <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
                        <TableRow>
                            <TableHeaderColumn>Name</TableHeaderColumn>
                            <TableHeaderColumn>Description</TableHeaderColumn>
                            <TableHeaderColumn>Link</TableHeaderColumn>
                            <TableHeaderColumn>Delete?</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody displayRowCheckbox={false}>
                        {result}
                    </TableBody>
                </Table>
            </div>
        )
    }
}

export default ConfigTable;