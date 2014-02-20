#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2012 IRSTV (FR CNRS 2488)
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package ${package};

import org.orbisgis.core.layerModel.MapContext;
import org.orbisgis.mapeditorapi.MapElement;
import org.orbisgis.viewapi.components.actions.DefaultAction;
import org.orbisgis.viewapi.edition.EditorDockable;
import org.orbisgis.viewapi.toc.ext.TocExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.util.Locale;

/**
 * Toc tool that shows layer count in console.
 */
public class HelloWorldTool extends DefaultAction {
    // Translation tool
    private static final I18n I18N = I18nFactory.getI18n(HelloWorldTool.class, Locale.getDefault(), I18nFactory.FALLBACK);
    // Prepend logger with popup in order to show the message in the popup frame
    private static final Logger LOGGER = LoggerFactory.getLogger("popup."+HelloWorldTool.class);
    private TocExt tocExt;

    /**
     * Constructor
     * @param tocExt Toc instance
     */
    public HelloWorldTool(TocExt tocExt) {
        super("A_PRINT_LAYERCOUNT", I18N.tr("Print layer count"),
                new ImageIcon(HelloWorldTool.class.getResource("message_small.png")));
        this.tocExt = tocExt;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Fetch map context
        MapContext mapContext = ((MapElement)((EditorDockable) tocExt).getEditableElement()).getMapContext();
        // Print layer count
        LOGGER.info(I18N.tr("Hello world there is {0} layers in your map context !", mapContext.getLayerModel().getLayerCount()));
    }
}
